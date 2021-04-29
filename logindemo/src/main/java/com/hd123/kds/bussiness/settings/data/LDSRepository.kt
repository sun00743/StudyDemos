package com.hd123.kds.bussiness.settings.data

import com.hd123.kds.base.data.Result
import com.hd123.kds.base.network.LDSRequestConfig
import com.hd123.kds.base.network.NetWorkMgr
import org.apache.log4j.Logger

class LDSRepository {

    private val logger = Logger.getLogger(LDSRepository::class.java)

    fun bindLDS(): Result<Unit> {
        return try {
            // TODO: 2021/4/29 获取最新订单信息
            val ldsService = NetWorkMgr.getService(LDSService::class.java, LDSRequestConfig())
            val response = ldsService.sendOrderToLDS()
            response.assertOk()
            return Result.Success(Unit)
        } catch (e: Exception) {
            logger.error(e)
            Result.Error(e)
        }
    }

}