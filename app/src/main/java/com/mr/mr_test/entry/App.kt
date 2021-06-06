package com.mr.mr_test.entry

import android.app.Application
import com.mr.mr_test.di.Scopes
import com.mr.mr_test.di.module.AppModule
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope()
    }

    private fun initToothpick() {
        Toothpick.setConfiguration(Configuration.forProduction())
    }

    private fun initAppScope() {
        Toothpick.openScope(Scopes.APP_SCOPE).installModules(AppModule())
    }
}