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

    fun coroutineLaunch(block: suspend CoroutineScope.() -> Unit) {
        loadingFlag.value = true
        viewModelScope.launch {
            block.invoke(this)
            loadingFlag.value = false
        }
    }

}