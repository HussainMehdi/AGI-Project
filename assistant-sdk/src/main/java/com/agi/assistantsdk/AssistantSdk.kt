package com.agi.assistantsdk

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.agi.assistantsdk.models.Action
import com.agi.assistantsdk.models.ActionResult
import com.agi.assistantsdk.models.LLMResponse
import com.agi.assistantsdk.models.PromptResult
import com.agi.assistantsdk.models.UiSnapshot

/**
 * Main entry point for the Assistant SDK.
 */
object AssistantSdk {
    @Volatile
    private var initialized = false
    
    private var config: AssistantSdkConfig = AssistantSdkConfig.default()
    private var currentActivity: Activity? = null
    private var captureEngine: ViewCaptureEngine? = null
    private var actionExecutor: ActionExecutor? = null
    private var ollamaClient: OllamaClient? = null
    private var ragClient: RagClient? = null
    private var promptBuilder: PromptBuilder? = null
    private var commandParser: LLMCommandParser? = null
    private var tokenMinimizer: TokenMinimizer? = null
    
    /**
     * Initialize the SDK with the application and configuration.
     */
    fun init(app: Application, config: AssistantSdkConfig = AssistantSdkConfig.default()) {
        if (initialized) {
            return
        }
        
        this.config = config
        val idGenerator = NodeIdGenerator()
        this.captureEngine = ViewCaptureEngine(config, idGenerator)
        this.actionExecutor = ActionExecutor(captureEngine!!)
        
        // Use RAG service if configured, otherwise use direct Ollama
        if (config.ragServiceBaseUrl != null) {
            this.ragClient = RagClient(config)
            this.ollamaClient = OllamaClient(config)
        } else {
            this.ollamaClient = OllamaClient(config)
        }
        
        this.promptBuilder = PromptBuilder()
        this.commandParser = LLMCommandParser()
        this.tokenMinimizer = TokenMinimizer()
        
        // Register activity lifecycle callback to track current activity
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (isActivityAllowed(activity)) {
                    currentActivity = activity
                }
            }
            
            override fun onActivityStarted(activity: Activity) {
                if (isActivityAllowed(activity)) {
                    currentActivity = activity
                }
            }
            
            override fun onActivityResumed(activity: Activity) {
                if (isActivityAllowed(activity)) {
                    currentActivity = activity
                }
            }
            
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                if (currentActivity == activity) {
                    currentActivity = null
                }
            }
        })
        
        initialized = true
    }
    
    /**
     * Bind a specific activity for capture and actions.
     * This is optional if using init() with lifecycle callbacks.
     */
    fun bind(activity: Activity) {
        checkInitialized()
        if (!isActivityAllowed(activity)) {
            throw IllegalArgumentException("Activity is not allowed: ${activity.javaClass.name}")
        }
        currentActivity = activity
    }
    
    /**
     * Capture the current UI state.
     */
    fun capture(): UiSnapshot {
        checkInitialized()
        val activity = currentActivity ?: throw IllegalStateException("No activity bound")
        val engine = captureEngine ?: throw IllegalStateException("SDK not properly initialized")
        
        return engine.capture(activity)
    }
    
    /**
     * Perform an action on the UI.
     */
    fun perform(action: Action): ActionResult {
        checkInitialized()
        val activity = currentActivity ?: return ActionResult.failure(
            com.agi.assistantsdk.models.ActionCode.INTERNAL_ERROR,
            "No activity bound"
        )
        val executor = actionExecutor ?: return ActionResult.failure(
            com.agi.assistantsdk.models.ActionCode.INTERNAL_ERROR,
            "SDK not properly initialized"
        )
        
        return executor.execute(activity, action)
    }
    
    /**
     * Execute a natural language prompt.
     * This will:
     * 1. Capture the current UI state
     * 2. Send the prompt and UI state to the LLM (Ollama)
     * 3. Parse the LLM response into commands
     * 4. Execute the commands
     * 
     * Note: This is a blocking call. Consider running it on a background thread.
     * 
     * @param userPrompt The natural language request from the user
     * @return PromptResult containing the results of executed actions
     */
    fun executePrompt(userPrompt: String): PromptResult {
        checkInitialized()
        val activity = currentActivity ?: return PromptResult(
            success = false,
            executedActions = emptyList(),
            error = "No activity bound"
        )
        
        val engine = captureEngine ?: return PromptResult(
            success = false,
            executedActions = emptyList(),
            error = "SDK not properly initialized"
        )
        
        val client = ollamaClient ?: return PromptResult(
            success = false,
            executedActions = emptyList(),
            error = "LLM client not initialized"
        )
        
        val builder = promptBuilder ?: return PromptResult(
            success = false,
            executedActions = emptyList(),
            error = "Prompt builder not initialized"
        )
        
        val parser = commandParser ?: return PromptResult(
            success = false,
            executedActions = emptyList(),
            error = "Command parser not initialized"
        )
        
        val executor = actionExecutor ?: return PromptResult(
            success = false,
            executedActions = emptyList(),
            error = "Action executor not initialized"
        )
        
        return try {
            // Step 1: Capture UI state
            val snapshot = engine.capture(activity)
            
            val llmResponse: LLMResponse
            
            // Check if using RAG service
            val ragClient = this.ragClient
            if (ragClient != null) {
                // Use RAG service: ingest snapshot and query
                val ingestResult = ragClient.ingestSnapshot(snapshot, config.ragSessionId)
                if (ingestResult.isFailure) {
                    return PromptResult(
                        success = false,
                        executedActions = emptyList(),
                        error = "Failed to ingest UI snapshot to RAG service: ${ingestResult.exceptionOrNull()?.message}"
                    )
                }
                
                // Query RAG service
                val queryResult = ragClient.query(userPrompt, config.ragSessionId)
                if (queryResult.isFailure) {
                    return PromptResult(
                        success = false,
                        executedActions = emptyList(),
                        error = "RAG service query failed: ${queryResult.exceptionOrNull()?.message}"
                    )
                }
                
                llmResponse = queryResult.getOrNull() ?: return PromptResult(
                    success = false,
                    executedActions = emptyList(),
                    error = "Empty RAG service response"
                )
            } else {
                // Use direct Ollama: minimize snapshot and build prompt
                val client = ollamaClient ?: return PromptResult(
                    success = false,
                    executedActions = emptyList(),
                    error = "LLM client not initialized"
                )
                
                // Step 1.5: Minimize snapshot to reduce token usage
                val minimizer = tokenMinimizer ?: return PromptResult(
                    success = false,
                    executedActions = emptyList(),
                    error = "Token minimizer not initialized"
                )
                val minimizedSnapshot = minimizer.minimize(snapshot)
                
                // Step 2: Build prompt with minimized snapshot
                val prompt = builder.buildPrompt(userPrompt, minimizedSnapshot)
                
                // Step 3: Send to LLM
                val llmResult = client.sendPrompt(prompt)
                if (llmResult.isFailure) {
                    return PromptResult(
                        success = false,
                        executedActions = emptyList(),
                        error = "LLM request failed: ${llmResult.exceptionOrNull()?.message}"
                    )
                }
                
                llmResponse = llmResult.getOrNull() ?: return PromptResult(
                    success = false,
                    executedActions = emptyList(),
                    error = "Empty LLM response"
                )
            }
            
            // Step 4: Parse commands
            val actions = parser.parseCommands(llmResponse.commands)
            if (actions.isEmpty()) {
                return PromptResult(
                    success = false,
                    executedActions = emptyList(),
                    error = "No valid commands found in LLM response"
                )
            }
            
            // Step 5: Execute actions
            val results = actions.map { action ->
                executor.execute(activity, action)
            }
            
            val allSuccessful = results.all { it.success }
            PromptResult(
                success = allSuccessful,
                executedActions = results,
                error = if (allSuccessful) null else "Some actions failed"
            )
        } catch (e: Exception) {
            PromptResult(
                success = false,
                executedActions = emptyList(),
                error = "Error executing prompt: ${e.message}"
            )
        }
    }
    
    private fun checkInitialized() {
        if (!initialized) {
            throw IllegalStateException("AssistantSdk not initialized. Call AssistantSdk.init() first.")
        }
    }
    
    private fun isActivityAllowed(activity: Activity): Boolean {
        val className = activity.javaClass.name
        
        // Check deny list first
        if (config.deniedActivities.isNotEmpty() && config.deniedActivities.contains(className)) {
            return false
        }
        
        // Check allow list (if non-empty, must be in list)
        if (config.allowedActivities.isNotEmpty() && !config.allowedActivities.contains(className)) {
            return false
        }
        
        return true
    }
}

