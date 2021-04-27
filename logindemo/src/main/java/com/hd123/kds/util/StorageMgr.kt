package com.hd123.kds.util

import android.content.Context
import android.util.Log
import com.hd123.kds.HDApplication
import com.hd123.kds.user.UserManager
import com.tencent.mmkv.MMKV

object StorageMgr {

    const val LEVEL_GLOBAL = "LEVEL_GLOBAL" // 全局级别
    const val LEVEL_USER = "LEVEL_USER" // 用户级别
    const val LEVEL_STORE = "LEVEL_STORE" // 门店级别

    fun init(context: Context) {
        val mmkvPath = MMKV.initialize(context)
        Log.d("mika_init", " mmkv init: $mmkvPath")
    }

    private fun getMMKV(): MMKV = try {
        MMKV.defaultMMKV()!!
    } catch (e: Exception) {
        val mmkvPath = MMKV.initialize(HDApplication.instance)
        Log.d("mika_init", " mmkv init: $mmkvPath")
        MMKV.defaultMMKV()!!
    }

    fun setBoolean(key: String, value: Boolean, level: String = LEVEL_GLOBAL) {
        getMMKV().putBoolean(formatKey(key, level), value)
    }

    fun getBoolean(key: String, def: Boolean = false): Boolean {
        return getMMKV().decodeBool(key, def)
    }

    fun set(key: String, value: String, level: String = LEVEL_GLOBAL) {
        getMMKV().putString(formatKey(key, level), value)
    }

    fun get(key: String): String {
        return getMMKV().decodeString(key) ?: ""
    }

    fun <T> set(key: String, value: T, level: String = LEVEL_GLOBAL) {
        val json = HDApplication.GSON.toJson(value)
        getMMKV().putString(formatKey(key, level), json)
    }

    fun <T> get(key: String, clazz: Class<T>): T? {
        val json = get(key)
        if (json.isNotBlank()) {
            return HDApplication.GSON.fromJson(json, clazz)
        }
        return null
    }

    private fun formatKey(key: String, level: String): String = when (level) {
        LEVEL_STORE ->
            // TODO: 2021/4/26
            key
//                temp = key + SessionMgr.getSetting().posNo
        LEVEL_USER ->
            key + UserManager.getUser().userName
        else ->
            key
    }


}