package com.hd123.kds.base.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object NetWorkMgr {

    private val serviceMap = HashMap<String, Any>()

    private val clientMap = hashMapOf<String, OkHttpClient>()

    private val gson by lazy {
        GsonBuilder().create()
    }

    fun <T> getService(service: Class<T>,
                       requestConfig: NetWorkRequestConfig = NetWorkRequestConfig.DEFAULT): T {
        serviceMap[service.simpleName]?.let {
            return it as T
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(requestConfig.getBaseUrl())
                .client(getClient(requestConfig))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val serviceImpl = retrofit.create(service)

        serviceMap[service.simpleName] = serviceImpl!!
        return serviceImpl
    }

    private fun getClient(requestConfig: NetWorkRequestConfig): OkHttpClient {
        clientMap[requestConfig.getTag()]?.let {
            return it
        }

        val client = OkHttpClient.Builder().run {
            retryOnConnectionFailure(false)
            connectTimeout(requestConfig.connectTimeout(), TimeUnit.MILLISECONDS)
            readTimeout(requestConfig.readTimeout(), TimeUnit.MILLISECONDS)
            writeTimeout(requestConfig.writeTimeout(), TimeUnit.MILLISECONDS)

            requestConfig.headerHandler()?.let {
                addInterceptor(HeaderInterceptor(it))
            }
            if (requestConfig.isLogEnable()) {
                val loggingInterceptor = HttpLoggingInterceptor()
//                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }

            build()
        }
        clientMap[requestConfig.getTag()] = client
        return client
    }

    class HeaderInterceptor(private val headerHandler: RequestHandler) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = headerHandler.onBeforeRequest(request, chain)

            val response = chain.proceed(request)
            return headerHandler.onAfterRequest(response, chain)
        }
    }
}