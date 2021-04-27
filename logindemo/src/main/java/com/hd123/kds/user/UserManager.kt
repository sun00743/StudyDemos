package com.hd123.kds.user

import com.hd123.kds.base.CACHE_KEY_USER
import com.hd123.kds.model.User
import com.hd123.kds.util.StorageMgr

object UserManager {

    private var user: User? = null

    fun getUser(): User {
        if (this.user == null) {
            val userCache = StorageMgr.get(CACHE_KEY_USER, User::class.java)
            if (userCache != null) {
                this.user = userCache
            }
        }
        if (user == null) {
            // TODO: 2021/4/26 LogOut
            throw Exception("用户信息异常，请重新登录")
        }
        return user!!
    }
    
    fun setUser(user: User) {
        this.user = user
        StorageMgr.set(CACHE_KEY_USER, user)
    }

    val isLoggedIn: Boolean
        get() = user != null

    fun getUserCache(): User? {
        return StorageMgr.get(CACHE_KEY_USER, User::class.java)
    }

}