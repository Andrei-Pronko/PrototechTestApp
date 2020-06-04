package com.mentarey.prototech.test.app.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PrototechApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PrototechApplication)
            modules(koinModules)
        }
    }
}