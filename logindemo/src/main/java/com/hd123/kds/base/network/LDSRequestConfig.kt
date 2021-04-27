package com.hd123.kds.base.network

import com.hd123.kds.util.Values
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.Exception

class LDSRequestConfig : NetWorkRequestConfig {

    private var serverUrl = ""

    override fun getBaseUrl(): String {
        if (serverUrl.isBlank()) {
            val ldsip = Values.LDSIP
            if (ldsip.isBlank()) {
                throw Exception("请配置客显IP")
            }
            serverUrl = "http://$ldsip:41210"
        }
        return serverUrl
    }

    override fun headerHandler(): RequestHandler {
        return object : RequestHeaderHandler() {
            override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
                return chain.request().newBuilder().run {
                    addHeader("Content-Type", CONTENT_JSON)
                    build()
                }
            }

            override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
                return response
            }
        }
    }

}