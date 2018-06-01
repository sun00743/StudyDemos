package com.mika.demo.study.livedata.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by mika on 2018/5/31
 */
class NameViewModel : ViewModel() {

    private lateinit var mCurrentName : MutableLiveData<String>

    fun getCurrentName(): MutableLiveData<String> {
        if (!::mCurrentName.isInitialized) {
            mCurrentName = MutableLiveData()
        }
        return mCurrentName
    }

}