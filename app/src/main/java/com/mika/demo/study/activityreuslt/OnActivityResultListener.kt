package com.mika.demo.study.activityreuslt

import android.content.Intent

/**
 * Created by mika on 2020/11/6.
 */
interface OnActivityResultListener {

    fun onResult(requestCode: Int, data: Intent?)

}