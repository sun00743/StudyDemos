package com.hd123.kds.base.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hd123.kds.widget.ResultObserver

class ResultLiveData<T : Any> : MutableLiveData<Result<T>>() {

    val resultData: T? by lazy {
        if (value is Result.Success) {
            return@lazy (value as Result.Success<T>).data
        }
        return@lazy null
    }

    fun observeWithToast(owner: LifecycleOwner, successCallback: (T) -> Unit,
                         error: ((msg: String) -> Unit)? = null) {
        super.observe(owner, object : ResultObserver<T> {

            override fun onError(msg: String) {
                error?.invoke(msg)
            }

            override fun onSuccess(data: T) {
                successCallback.invoke(data)
            }
        })
    }

    fun observeResult(owner: LifecycleOwner, successCallback: (T) -> Unit,
                         error: ((msg: String) -> Unit)? = null) {
        super.observe(owner, {
            if (it is Result.Success) {
                successCallback.invoke(it.data)
            } else {
                error?.invoke(it.getErrorMsg())
            }
        })
    }


}