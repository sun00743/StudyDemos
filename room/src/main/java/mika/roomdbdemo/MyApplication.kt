package mika.roomdbdemo

import android.app.Application
import mika.roomdbdemo.db.AppDataBase

class MyApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    val database: AppDataBase by lazy {
        AppDataBase.getInstance(this)
    }

}