package com.agi.sampleapp

import android.app.Application
import com.agi.assistantsdk.AssistantSdk
import com.agi.assistantsdk.AssistantSdkConfig

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize the Assistant SDK
        val config = AssistantSdkConfig.default()
        AssistantSdk.init(this, config)
    }
}

