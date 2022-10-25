package com.github.ivamhenrique.hearthstone

import android.app.Application
import com.github.ivamhenrique.hearthstone.di.DataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class HearthstoneApp : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@HearthstoneApp)
        }
        DataModules.loadModule()
    }
}