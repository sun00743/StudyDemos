package com.hd123.kds.model.lds

import java.lang.Exception

class BaseLDSResponse<T> {

    var rst = ""

    var code = -1

    var msg = ""

    var data: T? = null

    @Throws(Exception::class)
    fun assertOk(): T {
        if (code != 200 && rst != "ok") {
            throw Exception(msg)
        }
        return data!!
    }

}