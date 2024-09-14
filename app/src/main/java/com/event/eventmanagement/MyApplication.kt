package com.event.eventmanagement

import android.app.Application
import com.phonepe.intent.sdk.api.PhonePe
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        PhonePe.init(this)
    }

}