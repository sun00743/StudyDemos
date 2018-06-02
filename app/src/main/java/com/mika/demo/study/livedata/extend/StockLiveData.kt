package com.mika.demo.study.livedata.extend

import android.arch.lifecycle.LiveData
import android.util.Log


/**
 * Created by mika on 2018/6/1
 *
 * 创建StockManager并且在其初始化后就开始工作？
 * 观察者，在后台时停止观察。
 * 单例，多个activity/fragment共享。
 * 开启线程自动，持续更新数据？
 */
class StockLiveData() : LiveData<BigDecimal>() {

    private lateinit var mStockManager: StockManager

    private val mListener = object : StockManager.SimplePriceListener {
        override fun onPriceChanged(price: BigDecimal) {
            value = price
        }
    }

    /**
     * 创建StockManager并且在其初始化后就开始工作？
     */
    private constructor(symbol: Int) : this() {
        mStockManager = StockManager(symbol)
    }

    override fun onActive() {
        Log.d("mika_demo", "StockLiveData: onActive")

        mStockManager.requestPriceUpdates(mListener)
    }

//    fun updatePrice() {
//        mStockManager.updatePrice()
//    }

    fun changeKey(symbol: Int) {
        mStockManager.symbol = symbol
    }

    override fun onInactive() {
        Log.d("mika_demo", "StockLiveData: onInactive")

        mStockManager.removeUpdates(mListener)
    }

    companion object {

        private var instance: StockLiveData? = null

        @JvmStatic
        fun get(symbol: Int): StockLiveData {
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