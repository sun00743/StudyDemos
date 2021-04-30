package com.hd123.kds.bussiness.selectstore.data

import com.hd123.kds.BuildConfig
import com.hd123.kds.base.data.Result
import com.hd123.kds.base.network.NetWorkMgr
import com.hd123.kds.model.User
import com.hd123.kds.model.store.Department
import com.hd123.kds.model.store.Store
import com.hd123.kds.push.PushManager
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.Values
import org.apache.log4j.Logger
import java.io.IOException
import kotlin.math.log


class StoreSelectorRepository {

    private val logger = Logger.getLogger(StoreSelectorRepository::class.java)

    fun loadStore(): Result<List<Store>> {
        val result = try {
            val user = UserManager.getUser()
            // TODO: 2021/4/28  
//            val loadStore = NetWorkMgr.getService(StoreService::class.java).loadStore(user)
//            loadStore.data
            return Result.Success(fakeStore())
        } catch (e: Throwable) {
            return Result.Error(IOException(e))
        }

        return result
    }

    private fun fakeStore(): List<Store> {
        return arrayListOf(
                Store("asdas", "01"),
                Store("asdas", "01"),
                Store("asdas", "02"),
                Store("asd", "04324"),
                Store("asdas", "0345"),
                Store("fas", "045"),
                Store("asdas", "0564"),
                Store("gad", "065"),
                Store("asdas", "056"),
                Store("zxczxcasacsascacscazxczxcasacsascacscazxczxcasacsascacscazxczxcasacsascacsca", "056"),
                Store("zxczxcasacsascacscazxczxcasacsascacscazxczxcasacsascacscazxczxcasacsascacsca", "08"),
                Store("asdas", "012321"),
                Store("zxczxcasacsascacscazxczxcasacsascacscazxczxcasacsascacsca", "01dfg"),
                Store("asdas", "011"),
                Store("vzxvzxvzxvzxvzx", "011"),
        )
    }

    fun selectStore(store: Store) {
        UserManager.saveStore(store)
        logger.debug(" selectStore ${store.name}")
    }

    fun loadDepartment(): Result<List<Department>> {
        val result = try {
            val store = UserManager.getStore()
            // TODO: 2021/4/28
//            val loadStore = NetWorkMgr.getService(StoreService::class.java).loadStore(user)
//            loadStore.data
            return Result.Success(arrayListOf(Department("asdasd", "333"), Department("按时艰苦的拉伸", "444")))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }

        return result
    }

    fun selectDepartment(department: Department) {
        UserManager.saveDepartment(department)
        Values.isLogined = true
        logger.info(" finish login")
        logger.debug(" selectDepartment ${department.name}")
    }

}