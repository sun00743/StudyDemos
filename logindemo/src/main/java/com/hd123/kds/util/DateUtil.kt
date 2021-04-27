package com.hd123.kds.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {

        const val DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss"
        const val DATE_FORMAT_DAY = "yyyy-MM-dd"
        const val DATE_FORMAT_YEAR = "yyyy"

        fun formatDate(date: Date?, format: String?): String? {
            if (date == null || format.isNullOrEmpty())
                return null
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            return sdf.format(date)
        }
    }

}