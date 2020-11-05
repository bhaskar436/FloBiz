package com.bhaskar.flobiz.app

import android.app.Application
import com.bhaskar.flobiz.di.prefsModule
import com.bhaskar.flobiz.di.repoModule
import com.bhaskar.flobiz.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(viewModelModule, repoModule, prefsModule))
        }
    }
}