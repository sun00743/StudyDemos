package com.example.logindemo

import android.app.Application
import com.example.logindemo.push.PushManager

class HDApplication: Application() {

    companion object {
        lateinit var instance: HDApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        performInit()
    }

    private fun performInit() {
        PushManager.init(this)
        // TODO: 2021/4/26  
        PushManager.register()
    }


}