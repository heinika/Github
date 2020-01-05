package com.heinika.github

import android.app.Application
import kotlin.properties.Delegates

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}