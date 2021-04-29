package com.hd123.kds.user

import com.hd123.kds.base.CACHE_KEY_DEPARTMENT
import com.hd123.kds.base.CACHE_KEY_STORE
import com.hd123.kds.base.CACHE_KEY_USER
import com.hd123.kds.model.User
import com.hd123.kds.model.store.Department
import com.hd123.kds.model.store.Store
import com.hd123.kds.util.StorageMgr

object UserManager {

    private var user: User? = null

    private var store: Store? = null

    private var department: Department? = null

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

    fun saveStore(store: Store) {
        this.store = store
        StorageMgr.set(CACHE_KEY_STORE, store)
    }

    fun getStore(): Store? {
        return StorageMgr.get(CACHE_KEY_STORE, Store::class.java)
    }

    fun saveDepartment(department: Department) {
        this.department = department
        StorageMgr.set(CACHE_KEY_DEPARTMENT, department)
    }

    fun getDepartment(): Department? {
        return StorageMgr.get(CACHE_KEY_DEPARTMENT, Department::class.java)
    }


}