package com.hd123.kds.base.network

import com.hd123.kds.BuildConfig
import com.hd123.kds.util.Values
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

const val CONTENT_JSON = "application/json"

open class RequestHeaderHandler : RequestHandler {

    override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
        return chain.request().newBuilder().run {
            addHeader(
                    "App-Version",
                    "andorid-" + BuildConfig.VERSION_NAME + "_" + BuildConfig.VERSION_CODE
            )
            addHeader("Content-Type", CONTENT_JSON)
            // TODO: 2021/4/27
//        addHeader("trace_id", getTraceId())
//        if (Values.posNo.isNotEmpty()) {
//            addHeader("deviceId", StorageMgr.posNo)
//        }
//        if (Values.authorization.isNotEmpty()) {
//            addHeader("Authorization", StorageMgr.authorization)
//        }
            build()
        }
    }

    @Throws(IOException::class)
    override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
        val authorization = response.header("authorization")
        if (authorization != null && authorization != "") {
            // TODO: 2021/4/27
//            Values.authorization = authorization
        }

        val responseCode = response.code()
        when {
//            responseCode > 400 ->
//            responseCode == 503 ->
//            responseCode > 300 ->
        }
        return response
    }
}