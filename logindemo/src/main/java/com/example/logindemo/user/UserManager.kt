package com.example.logindemo.user

import com.example.logindemo.model.User
import java.lang.Exception

object UserManager {
    
    fun getUser(): User {
        // TODO: 2021/4/26 load User from Storage
//        StringUtil.hexToBytes
        val user = User("", "", "")
        if (user == null) {
            // TODO: 2021/4/26 LogOut 
            throw Exception("用户信息异常，请重新登录")
        }
        return user
    }
    
    fun setUser(user: User) {
//        StringUtil.bytes2Hex
        // TODO: 2021/4/26 set user to Storage
    }

}