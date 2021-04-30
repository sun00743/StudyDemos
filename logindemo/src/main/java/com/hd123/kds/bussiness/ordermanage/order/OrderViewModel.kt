package com.hd123.kds.bussiness.ordermanage.order

import com.hd123.kds.base.BaseViewModel
import com.hd123.kds.base.data.ResultLiveData
import com.hd123.kds.bussiness.ordermanage.order.data.OrderRepository
import com.hd123.kds.model.Order

class OrderViewModel : BaseViewModel() {

    var mode: Int = LOAD_DATA_TYPE_ALL

    private val orderRepository by lazy { OrderRepository() }

    val orderList = ResultLiveData<List<Order>>()

    fun loadOrder() {
        /// TODO: 2021/4/30 orderRepository
        coroutineLaunch {
            val result = orderRepository.loadOrderList(mode)
            orderList.value = result
        }
    }

}