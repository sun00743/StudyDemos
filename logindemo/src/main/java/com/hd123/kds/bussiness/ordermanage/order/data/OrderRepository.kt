package com.hd123.kds.bussiness.ordermanage.order.data

import com.hd123.kds.base.data.Result
import com.hd123.kds.model.Order
import com.hd123.kds.util.MyStringUtil
import org.apache.log4j.Logger
import java.io.IOException
import java.lang.Exception
import kotlin.random.Random

class OrderRepository {

    private val logger = Logger.getLogger(OrderRepository::class.java)

    fun loadOrderList(mode: Int): Result<List<Order>> {
        val result = try {
            // TODO: 2021/4/30 service load order list
            // TODO: 2021/5/5 send to LDS
//                service.load(mode)
            return Result.Success(fakeOrder())
        } catch (e: Exception) {
            logger.error(e)
            return Result.Error(IOException(e))
        }
        return result
    }

    fun searOrder(code: String): Result<List<Order>> {
        val result = try {
            // TODO: 2021/4/30 service load order list
//                service.searOrder(mode)
            return Result.Success(fakeOrder())
        } catch (e: Exception) {
            logger.error(e)
            return Result.Error(IOException(e))
        }
        return result
    }

    private fun fakeOrder(): List<Order> = arrayListOf(
            Order(MyStringUtil.generateUuid(), "00" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:12:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "060" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:12:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "050" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:12:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "040" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:12:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "030" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:12:12", Order.STATE_READY),
            Order(MyStringUtil.generateUuid(), "00" + Random.nextInt(100).toString(),
                    Order.FROM_STORE, "04.21 8:12:12", Order.STATE_READY),
            Order(MyStringUtil.generateUuid(), "00" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 2:12:12", Order.STATE_READY),
            Order(MyStringUtil.generateUuid(), "020" + Random.nextInt(100).toString(),
                    Order.FROM_STORE, "04.21 1:12:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "00" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:12:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "00" + Random.nextInt(100).toString(),
                    Order.FROM_STORE, "04.21 12:13:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "001" + Random.nextInt(100).toString(),
                    Order.FROM_STORE, "04.21 12:14:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "00" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:15:12", Order.STATE_COOKING),
            Order(MyStringUtil.generateUuid(), "007" + Random.nextInt(100).toString(),
                    Order.FROM_ONLINE, "04.21 12:16:12", Order.STATE_READY),
    )

}