package com.hd123.kds

import android.app.Application
import com.hd123.kds.push.PushManager
import com.hd123.kds.util.StorageMgr
import com.google.gson.GsonBuilder

class HDApplication: Application() {

    companion object {
        lateinit var instance: HDApplication
            private set

        val GSON by lazy {
            GsonBuilder().create()
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        performInit()
    }

    private fun performInit() {
        StorageMgr.init(this)
        PushManager.init(this)
        // TODO: 2021/4/26  
        PushManager.register()
    }


}