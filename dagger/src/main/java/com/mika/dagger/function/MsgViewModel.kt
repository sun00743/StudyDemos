package com.mika.dagger.function

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by mika on 2019/10/14.
 */
class MsgViewModel(name: String) : ViewModel() {

    init {
        Log.d("mika", "viewModel init: $name")
    }

    private val mMsgData: MutableLiveData<Msg> by lazy {
        MutableLiveData<Msg>()
    }

    fun getCurrentMsg(): MutableLiveData<Msg> = mMsgData

}

data class Msg

@Inject
constructor(@Named("msgName2") var content: String,
            val id: Int)