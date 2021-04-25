package com.mika.demo.study.livedata.base

import android.util.Log
import androidx.lifecycle.*


/**
 * Created by mika on 2018/5/31
 */
class NameViewModel : ViewModel(), LifecycleObserver {

    private var isFirstStated = true

    private var mCurrentName : MutableLiveData<String> = MutableLiveData()

    fun getCurrentName(): MutableLiveData<String> = mCurrentName


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadCurrentName() {
        Log.d("mika_NameViewModel", "create call: $this")
        mCurrentName.value = "mika_001"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadCurrentNameAgain() {
        mCurrentName.value = "mika_002"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun started() {
        Log.d("mika_lifecycle", "started event")
        if (isFirstStated) {
            isFirstStated = false
            mCurrentName.value = "mika_started"
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d("mika_lifecycle", "destroy event")
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("mika_NameViewModel", "cleared")
    }
}