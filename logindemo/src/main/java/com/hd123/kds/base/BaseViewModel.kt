package com.hd123.kds.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    val errorMsg = MutableLiveData<String>()

    val loadingFlag = MutableLiveData<Boolean>()

    protected var isLoading = false

    fun coroutineLaunch(loadingFlag : MutableLiveData<Boolean> = this.loadingFlag,
                        block: suspend CoroutineScope.() -> Unit) {
        if (isLoading) return
        isLoading = true
        loadingFlag.value = true
        viewModelScope.launch {
            block.invoke(this)
            loadingFlag.value = false
            isLoading = false
        }
    }

}