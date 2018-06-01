package com.mika.demo.study.livedata.extend

import android.arch.lifecycle.LiveData
import android.util.Log


/**
 * Created by mika on 2018/6/1
 */
class StockLiveData() : LiveData<BigDecimal>(){

    private lateinit var mStockManager: StockManager

    private val mListener = object : StockManager.SimplePriceListener{
        override fun onPriceChanged(price: BigDecimal) {
            value = price
        }
    }

    private constructor(symbol: String) : this() {
        mStockManager = StockManager(symbol)
    }

    override fun onActive() {
        Log.d("mika_demo", "StockLiveData: onActive")

        mStockManager.requestPriceUpdates(mListener)
    }

    override fun onInactive() {
        Log.d("mika_demo", "StockLiveData: onInactive")

        mStockManager.removeUpdates(mListener)
    }

    companion object {

        private var instance: StockLiveData? = null

        @JvmStatic
        fun get(symbol: String): StockLiveData {
            if (instance == null) {
                instance = StockLiveData(symbol)
            }
            return instance as StockLiveData
        }

    }

    private object Holder {
        val INSTANCE = StockLiveData()
    }

}