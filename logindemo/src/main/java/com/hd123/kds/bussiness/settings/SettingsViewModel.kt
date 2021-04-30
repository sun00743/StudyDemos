package com.hd123.kds.bussiness.settings

import com.hd123.kds.BuildConfig
import com.hd123.kds.R
import com.hd123.kds.base.BaseViewModel
import com.hd123.kds.base.data.Result
import com.hd123.kds.base.data.ResultLiveData
import com.hd123.kds.base.network.LDSRequestConfig
import com.hd123.kds.bussiness.selectstore.data.StoreSelectorRepository
import com.hd123.kds.bussiness.settings.data.LDSRepository
import com.hd123.kds.login.data.LoginRepository
import com.hd123.kds.model.store.Department
import com.hd123.kds.model.store.Store
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.Values
import com.hd123.kds.widget.ToastHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.log4j.Logger

class SettingsViewModel : BaseViewModel() {

    private val logger = Logger.getLogger(SettingsViewModel::class.java)

    var currentStore: Store? = UserManager.getStore()

    var currentDepartment: Department? = UserManager.getDepartment()

    val storeList = ResultLiveData<List<Store>>()
    val departmentList = ResultLiveData<List<Department>>()
    val logoutFlag = ResultLiveData<Boolean>()

    private val selectorRepository = StoreSelectorRepository()

    private val ldsRepository = LDSRepository()

    private val loginRepository = LoginRepository()

    fun loadStore() {
        coroutineLaunch {
            val storeResult = withContext(Dispatchers.IO) { selectorRepository.loadStore() }
            storeList.value = storeResult
        }
    }

    fun loadDepartment() {
        coroutineLaunch {
            val reuslt = withContext(Dispatchers.IO) { selectorRepository.loadDepartment() }
            departmentList.value = reuslt
        }
    }

    fun selectStore(position: Int) {
        storeList.resultData?.get(position)?.apply {
            selectorRepository.selectStore(this)
            currentStore = this
        }
    }

    fun bindLDS(input: String) {
        Values.LDSIP = input
        if (BuildConfig.DEBUG) {
            logger.info(" 设置客显地址: " + LDSRequestConfig().getBaseUrl())
        }
        coroutineLaunch {
            val bindLDSResult = ldsRepository.bindLDS()
            if (bindLDSResult is Result.Success) {
                // TODO: 2021/4/29
                ToastHolder.toast(R.string.settings_success_bind_lds)
            } else {
                errorMsg.value = bindLDSResult.getErrorMsg()
            }
        }
    }

    fun selectDepartment(position: Int) {
        departmentList.resultData?.get(position)?.apply {
            selectorRepository.selectDepartment(this)
            currentDepartment = this
        }
    }

    fun logout() {
        coroutineLaunch {
            loginRepository.logout()
        }
    }

}