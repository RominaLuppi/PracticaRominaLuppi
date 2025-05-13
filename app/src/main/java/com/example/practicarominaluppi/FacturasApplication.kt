package com.example.practicarominaluppi

import android.app.Application
import com.google.gson.internal.GsonBuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FacturasApplication : Application(){
    override fun onCreate() {
        super.onCreate()

    }
}
