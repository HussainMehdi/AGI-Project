package com.agi.ridehailingapp

import android.app.Application
import com.agi.assistantsdk.AssistantSdk
import com.agi.assistantsdk.AssistantSdkConfig

class RideHailingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = AssistantSdkConfig.default()
        AssistantSdk.init(this, config)
    }
}

