package com.mika.dagger

import android.app.Activity

/**
 * Created by mika on 2019/10/13.
 */
interface BaseActivityComponent<T: Activity> {
    fun init(activity: T)
}