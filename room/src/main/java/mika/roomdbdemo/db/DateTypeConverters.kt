package mika.roomdbdemo.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 支持时区的时间转换
 */

object DateTypeConverters {

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toOffsetDateTime(dataString: String?): OffsetDateTime? {
        return dataString?.let {
            OffsetDateTime.from(formatter?.parse(it))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toDate(dateMillis: Long?): Date? {
        return dateMillis?.let { Date(it) }
    }

    @TypeConverter
    fun toLong(date: Date?): Long? = date?.time


}