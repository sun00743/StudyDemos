package com.mika.demo.study.livedata.extend

import android.os.Handler
import android.os.Looper

/**
 * Created by mika on 2018/6/1
 */
class StockManager(var symbol: Int) {

    private val mUpdates = arrayListOf<SimplePriceListener>()

    private val messageUpdate = 1
    private val mHandler = Handler(Looper.getMainLooper()) {
        if (it.what == messageUpdate) {
            updatePrice(symbol)
            return@Handler false
        }
        return@Handler true
    }

    init {
        updatePrice(symbol)
    }

    private fun updatePrice(symbol: Int) {
        this.symbol += symbol
        mUpdates.forEach {
            it.onPriceChanged(BigDecimal(this.symbol))
        }
        mHandler.sendEmptyMessageDelayed(messageUpdate, 1000)
    }

    fun requestPriceUpdates(listener: SimplePriceListener) {
        if (mUpdates.contains(listener)) {
            return
        }
        mUpdates.add(listener)
    }

    fun removeUpdates(listener: SimplePriceListener) {
        if (mUpdates.contains(listener)) {
            mUpdates.remove(listener)
        }
    }

    interface SimplePriceListener {
        fun onPriceChanged(price: BigDecimal)
    }
}