package com.hd123.kds.bussiness.ordersearch

import com.hd123.kds.base.BaseViewModel
import com.hd123.kds.base.data.ResultLiveData
import com.hd123.kds.bussiness.ordermanage.order.data.OrderRepository
import com.hd123.kds.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderSearchViewModel: BaseViewModel() {

    val orderList = ResultLiveData<List<Order>>()

    private val orderRepository by lazy { OrderRepository() }

    fun search(code: String) {
        coroutineLaunch {
            val result = withContext(Dispatchers.IO) {
                orderRepository.searOrder(code)
            }
            orderList.value = result
        }

    }

}