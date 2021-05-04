package com.hd123.kds.push

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hd123.kds.HDApplication
import com.hd123.kds.user.UserManager
import com.hd123.push.ali.AliMessageReceiver
import com.hd123.push.ali.AliPushMgr
import com.hd123.push.listener.IAliBindCallback
import com.hd123.push.model.BindResult
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.log4j.Logger
import kotlin.coroutines.resume

object PushManager {

    private val logger = Logger.getLogger(PushManager::class.java)

    val token = MutableLiveData<String>()

    private val pushObserver: ArrayList<PushObserver> = arrayListOf()

    fun init(context: Context) {
        AliPushMgr.init(context, object : IAliBindCallback {
            override fun callback(param: BindResult) {
                logger.info("${HDApplication.GSON.toJson(param)} ${AliPushMgr.getDevicesId()}")
            }
        })
    }

    fun register() {
        Log.d("mika_push", " PushManager register")
        AliMessageReceiver.apply { callback = AliReceiver(pushObserver) }
    }

    fun unRegister() {
        // TODO: 2021/4/26
    }

    fun addListener(listener: PushObserver) {
        if (!pushObserver.contains(listener)) {
            pushObserver.add(listener)
        }
    }

    fun removeListener(listener: PushObserver) {
        if (pushObserver.contains(listener)) {
            pushObserver.remove(listener)
        }
    }

    /**
     * 绑定推送服务
     */
    suspend fun bindAccount() = suspendCancellableCoroutine<BindResult> { cont ->
        val user = UserManager.getUser()
        // TODO: 2021/4/27
        AliPushMgr.bindAccount("bindAccount", object : IAliBindCallback {
            override fun callback(param: BindResult) {
                cont.resume(param)
            }
        })
    }

    interface PushObserver {
        fun onReceiver()
    }

}