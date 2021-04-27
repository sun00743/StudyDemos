package com.hd123.kds.base.network

interface NetWorkRequestConfig {

    fun getTag(): String = this.javaClass.name

    fun getBaseUrl(): String

    fun connectTimeout(): Long = 10 * 1000L

    fun readTimeout(): Long = 10 * 1000L

    fun writeTimeout(): Long = 10 * 1000L

    fun isLogEnable(): Boolean = false

    fun headerHandler(): RequestHandler?

    companion object {
        val DEFAULT: NetWorkRequestConfig = object : NetWorkRequestConfig {

            // TODO: 2021/4/27 set server url
            private val serverUrl = ""

            override fun getBaseUrl(): String {
                return serverUrl
            }

            override fun headerHandler(): RequestHandler {
                return RequestHeaderHandler()
            }
        }
    }

}