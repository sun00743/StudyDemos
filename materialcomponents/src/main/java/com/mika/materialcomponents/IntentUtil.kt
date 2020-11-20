package com.mika.materialcomponents

import android.app.Activity
import android.content.Intent
import android.view.WindowMetrics

/**
 * Created by mika on 2020/10/21.
 */

/**
 * activity 直接跳转
 */
fun Activity.goActivity(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}

fun Activity.intToDp(value: Int): Float = resources.displayMetrics.density * value.toFloat()
