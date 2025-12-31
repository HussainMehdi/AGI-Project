package com.agi.assistantsdk

import com.agi.assistantsdk.models.LLMResponse
import com.agi.assistantsdk.models.LLMCommand
import com.agi.assistantsdk.models.UiSnapshot
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Client for communicating with the RAG service.
 * This service uses vector embeddings to retrieve only relevant UI components,
 * significantly reducing token usage compared to sending the entire UI snapshot.
 */
internal class RagClient(private val config: AssistantSdkConfig) {
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(config.llmTimeoutSeconds.toLong(), TimeUnit.SECONDS)
        .readTimeout(config.llmTimeoutSeconds.toLong(), TimeUnit.SECONDS)
        .writeTimeout(config.llmTimeoutSeconds.toLong(), TimeUnit.SECONDS)
        .build()
    
    private val gson = Gson()
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    private val multipartMediaType = "multipart/form-data".toMediaType()
    
    /**
     * Ingests a UI snapshot into the RAG service's vector database.
     * This should be called once per UI capture before querying.
     */
    fun ingestSnapshot(snapshot: UiSnapshot, sessionId: String? = null): Result<IngestResponse> {
        val snapshotJson = gson.toJson(snapshot)
        
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "snapshot.json",
                snapshotJson.toRequestBody("application/json".toMediaType())
            )
            .apply {
                if (sessionId != null) {
                    addFormDataPart("session_id", sessionId)
                }
            }
            .build()
        
        val request = Request.Builder()
            .url("${config.ragServiceBaseUrl}/ingest")
            .post(requestBody)
            .build()
        
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return Result.failure(
                        IOException("Unexpected code $response: ${response.message}")
                    )
                }
                
                val responseBody = response.body?.string() ?: return Result.failure(
                    IOException("Empty response body")
                )
                
                val ingestResponse = gson.fromJson(responseBody, IngestResponse::class.java)
                Result.success(ingestResponse)
            }
        } catch (e: JsonSyntaxException) {
            Result.failure(IOException("Failed to parse response: ${e.message}", e))
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(IOException("Unexpected error: ${e.message}", e))
        }
    }
    
    /**
     * Queries the RAG service with a user prompt.
     * The service will retrieve only relevant UI components and return LLM commands.
     */
    fun query(userPrompt: String, sessionId: String? = null, topK: Int = 10): Result<LLMResponse> {
        val requestMap = mutableMapOf<String, Any>(
            "user_prompt" to userPrompt,
            "top_k" to topK
        )
        if (sessionId != null) {
            requestMap["session_id"] = sessionId
        }
        val requestBody = gson.toJson(requestMap)
        
        val request = Request.Builder()
            .url("${config.ragServiceBaseUrl}/query")
            .post(requestBody.toRequestBody(jsonMediaType))
            .build()
        
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return Result.failure(
                        IOException("Unexpected code $response: ${response.message}")
                    )
                }
                
                val responseBody = response.body?.string() ?: return Result.failure(
                    IOException("Empty response body")
                )
                
                // Parse RAG service response
                val ragResponse = gson.fromJson(responseBody, RagQueryResponse::class.java)
                
                // Convert commands from Map to LLMCommand objects
                val commands = ragResponse.commands.mapNotNull { cmdMap ->
                    try {
                        val action = cmdMap["action"] as? String ?: return@mapNotNull null
                        LLMCommand(
                            action = action,
                            nodeId = cmdMap["nodeId"] as? String,
                            text = cmdMap["text"] as? String,
                            direction = cmdMap["direction"] as? String,
                            amount = (cmdMap["amount"] as? Number)?.toInt()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                
                val llmResponse = LLMResponse(commands = commands)
                Result.success(llmResponse)
            }
        } catch (e: JsonSyntaxException) {
            Result.failure(IOException("Failed to parse LLM response: ${e.message}", e))
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(IOException("Unexpected error: ${e.message}", e))
        }
    }
    
    /**
     * Checks if the RAG service is available and healthy.
     */
    fun checkHealth(): Result<Boolean> {
        val request = Request.Builder()
            .url("${config.ragServiceBaseUrl}/health")
            .get()
            .build()
        
        return try {
            client.newCall(request).execute().use { response ->
                Result.success(response.isSuccessful)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private data class IngestResponse(
        val status: String,
        val session_id: String?,
        val nodes_processed: Int,
        val timestamp: String
    )
    
    private data class RagQueryResponse(
        val commands: List<Map<String, Any>>,
        val relevant_components: List<Map<String, Any>>?,
        val token_usage: Map<String, Any>?,
        val session_id: String?
    )
}

