package com.am.bbsa.service.di

import android.app.Application
import com.am.bbsa.service.di.KoinModule.databaseModule
import com.am.bbsa.service.di.KoinModule.uiModule
import com.am.bbsa.service.source.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        UserPreferences.getInstance().init(this)
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    uiModule
                )
            )
        }
    }
}