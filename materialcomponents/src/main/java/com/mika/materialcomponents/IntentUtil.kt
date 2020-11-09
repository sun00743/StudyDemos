package com.mika.materialcomponents

import android.app.Activity
import android.content.Intent

/**
 * Created by mika on 2020/10/21.
 */

/**
 * activity 直接跳转
 */
fun Activity.goActivity(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}