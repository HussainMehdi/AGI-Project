package com.agi.calendarapp

import android.app.Application
import com.agi.assistantsdk.AssistantSdk
import com.agi.assistantsdk.AssistantSdkConfig

class CalendarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = AssistantSdkConfig.default()
        AssistantSdk.init(this, config)
    }
}

