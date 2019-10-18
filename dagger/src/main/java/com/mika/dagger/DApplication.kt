package com.mika.dagger

import android.app.Application
import android.content.Context

/**
 * Created by mika on 2019/10/13.
 */
class DApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    private val component: BaseComponent by lazy {
        DaggerBaseComponent.create()
    }

    companion object {

        @JvmStatic
        fun baseComponent(context: Context): BaseComponent {
            return (context.applicationContext as DApplication).component
        }

    }

}