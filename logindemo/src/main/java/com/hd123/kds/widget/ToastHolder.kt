package com.hd123.kds.widget

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.annotation.StringRes
import com.hd123.kds.HDApplication
import java.util.*

object ToastHolder {

    private var toast: Toast? = null

    private var isShowing = false

    private val msgQueue = LinkedList<String>()

    private val handler = ToastHandler()

    fun toast(@StringRes resId: Int) {
        toast(HDApplication.instance.getString(resId))
    }

    /**
     * @param   compensate  未立即显示的，补偿展示
     */
    fun toast(content: String, compensate: Boolean = false) {
        if (toast == null) {
            toast = Toast.makeText(HDApplication.instance, content, Toast.LENGTH_SHORT)
        }
        if (!compensate) {
            msgQueue.addFirst(content)
        }

        if (isShowing) {
            return
        }
        toast?.apply {
            setText(content)
            duration = Toast.LENGTH_SHORT
            show()

            isShowing = true
            handler.sendEmptyMessageDelayed(0, 3200L)
        }
    }

    private class ToastHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            isShowing = false
            msgQueue.pollLast()
            if (msgQueue.isNotEmpty()) {
                toast(msgQueue.peekLast()!!, true)
            }
        }
    }

}