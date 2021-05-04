package com.hd123.kds.bussiness.ordermanage.order

import com.hd123.kds.base.BaseViewModel
import com.hd123.kds.base.data.ResultLiveData
import com.hd123.kds.bussiness.ordermanage.order.data.OrderRepository
import com.hd123.kds.model.Order
import com.hd123.kds.push.PushManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderViewModel : BaseViewModel(), PushManager.PushObserver {

    var mode: Int = LOAD_DATA_TYPE_ALL

    private val orderRepository by lazy { OrderRepository() }

    val orderList = ResultLiveData<List<Order>>()

    fun init(mode: Int) {
        this.mode = mode

        if (mode == LOAD_DATA_TYPE_ALL || mode == LOAD_DATA_TYPE_COOKING) {
            PushManager.addListener(this)
        }
    }

    fun loadOrder() {
        /// TODO: 2021/4/30 orderRepository
        coroutineLaunch {
            val result = withContext(Dispatchers.IO) {
                orderRepository.loadOrderList(mode)
            }
            orderList.value = result
        }
    }

    /**
     *
     */
    override fun onReceiver() {
        // TODO: 2021/5/5
    }

    override fun onCleared() {
        super.onCleared()
        PushManager.removeListener(this)
    }

}