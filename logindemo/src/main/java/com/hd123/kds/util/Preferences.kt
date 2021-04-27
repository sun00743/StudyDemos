package com.hd123.kds.util

import com.hd123.kds.base.PREF_IS_LOGINED
import com.hd123.kds.base.PREF_LDS_IP
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preferences<T>(val key: String, val def: T)
    : ReadWriteProperty<Any, T> {

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        when (value) {
            is Boolean -> StorageMgr.setBoolean(key, value)
            else -> StorageMgr.set(key, value.toString())
        }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val value = when (def) {
            is Boolean -> StorageMgr.getBoolean(key, def)
            is Int -> StorageMgr.get(key).toInt()
            is Long -> StorageMgr.get(key).toLong()
            is Float -> StorageMgr.get(key).toFloat()
            else -> StorageMgr.get(key)
        }
        return value as T
    }

}

class Values {
    companion object {
        var isLogined by Preferences(PREF_IS_LOGINED, false)
        var LDSIP by Preferences(PREF_LDS_IP, "")
    }

}