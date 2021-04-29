package com.hd123.kds.widget

import androidx.lifecycle.Observer
import com.hd123.kds.base.data.Result

interface ResultObserver<T: Any>: Observer<Result<T>> {

    override fun onChanged(t: Result<T>?) {
        if (t is Result.Error) {
            t.exception.message?.let {
                ToastHolder.toast(it)
                onError(it)
            }
            return
        }
        onSuccess((t as Result.Success).data)
    }

    fun onSuccess(data: T)
    fun onError(msg: String)

}