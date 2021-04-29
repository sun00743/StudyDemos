package com.hd123.kds.util

import java.util.*

class MyStringUtil {
    companion object {
        fun generateUuid(): String {
            return UUID.randomUUID().toString().replace("-".toRegex(), "")
        }
    }
}