package com.hd123.kds.model

class BaseResponse<T> {

    var code: Int = -1

    var data: T? = null

}