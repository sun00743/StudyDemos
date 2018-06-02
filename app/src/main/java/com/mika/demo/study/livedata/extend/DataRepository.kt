package com.mika.demo.study.livedata.extend

import android.arch.lifecycle.LiveData

/**
 * Created by mika on 2018/6/2.
 */
class DataRepository {

    fun getData(seed: Int): LiveData<BigDecimal> {

/*
        when(seed) {
            //... return some liveData with seed
        }
*/

        val liveData = StockLiveData.get(seed)
        liveData.changeKey(seed)
        return liveData
    }

}