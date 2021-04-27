package com.hd123.kds.push

import com.hd123.kds.HDApplication
import com.hd123.push.listener.IAliReceiverCallback
import com.hd123.push.model.PushParam
import org.apache.log4j.Logger

class AliReceiver : IAliReceiverCallback {

    private val logger = Logger.getLogger(AliReceiver::class.java)
    
    override fun notifyCallback(param: PushParam) {
        handle(param)
    }

    private fun handle(param: PushParam) {
        val msg = HDApplication.GSON.toJson(param)
        logger.info("接收到通知/消息 $msg")
        // TODO: 2021/4/27  
//        handleParam(param.extraMap)
    }

}
