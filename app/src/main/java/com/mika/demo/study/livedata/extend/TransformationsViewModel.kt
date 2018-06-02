package com.mika.demo.study.livedata.extend

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

/**
 * Created by mika on 2018/6/2.
 */
class TransformationsViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val keyData = MutableLiveData<Int>()

    val data: LiveData<BigDecimal> = Transformations.switchMap(keyData, {
        dataRepository.getData(it)
    })

    fun setKeyData(keyInt: Int){
        keyData.value = keyInt
    }

}