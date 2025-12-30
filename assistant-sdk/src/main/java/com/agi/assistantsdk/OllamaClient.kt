package com.agi.assistantsdk

import com.agi.assistantsdk.models.LLMResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Client for communicating with Ollama API.
 */
internal class OllamaClient(private val config: AssistantSdkConfig) {
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(config.llmTimeoutSeconds.toLong(), TimeUnit.SECONDS)
        .readTimeout(config.llmTimeoutSeconds.toLong(), TimeUnit.SECONDS)
        .writeTimeout(config.llmTimeoutSeconds.toLong(), TimeUnit.SECONDS)
        .build()
    
    private val gson = Gson()
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    
    /**
     * Sends a prompt to Ollama and returns the response.
     */
    fun sendPrompt(prompt: String): Result<LLMResponse> {
        val requestBody = gson.toJson(
            mapOf(
                "model" to config.ollamaModel,
                "prompt" to prompt,
                "stream" to false,
                "format" to "json"
            )
        )
        
        val request = Request.Builder()
            .url("${config.ollamaBaseUrl}/api/generate")
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
                
                // Parse Ollama response - it returns { "response": "...json..." }
                val ollamaResponse = gson.fromJson(responseBody, OllamaApiResponse::class.java)
                
                // Extract JSON from the response field
                var jsonResponse = ollamaResponse.response.trim()
                
                // Remove markdown code blocks if present
                jsonResponse = jsonResponse.removePrefix("```json").removePrefix("```")
                jsonResponse = jsonResponse.removeSuffix("```").trim()
                
                // Parse the JSON commands
                val llmResponse = gson.fromJson(jsonResponse, LLMResponse::class.java)
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
    
    private data class OllamaApiResponse(
        val response: String
    )
}

