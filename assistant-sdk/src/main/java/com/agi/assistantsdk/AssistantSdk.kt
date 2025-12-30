package com.agi.assistantsdk

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.agi.assistantsdk.models.Action
import com.agi.assistantsdk.models.ActionResult
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

