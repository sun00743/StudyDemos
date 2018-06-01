package com.mika.demo.study.livedata.extend

/**
 * Created by mika on 2018/6/1
 */
class StockManager(var symbol: String) {

    private val mUpdates = arrayListOf<SimplePriceListener>()

    fun requestPriceUpdates(listener: SimplePriceListener){
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