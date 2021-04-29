package com.hd123.kds.bussiness.selectstore

import com.hd123.kds.base.BaseViewModel
import com.hd123.kds.base.data.ResultLiveData
import com.hd123.kds.bussiness.selectstore.StoreSelectorActivity.Companion.SELECT_MODE_STORE
import com.hd123.kds.bussiness.selectstore.data.StoreSelectorRepository
import com.hd123.kds.model.store.Department
import com.hd123.kds.model.store.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoreSelectorViewModel(var mode: Int) : BaseViewModel() {

    private val selectorRepository = StoreSelectorRepository()

    val storeList = ResultLiveData<List<Store>>()
    val departmentList = ResultLiveData<List<Department>>()

    fun loadData() {
        if (isStoreMode()) {
            coroutineLaunch {
                val store = withContext(Dispatchers.IO) { selectorRepository.loadStore() }
                val department = withContext(Dispatchers.IO) { selectorRepository.loadDepartment() }

                storeList.value = store
                departmentList.value = department
            }
        } else {
            loadDepartment()
        }
    }

    private fun loadDepartment() {
        coroutineLaunch {
            val department = withContext(Dispatchers.IO) { selectorRepository.loadDepartment() }
            departmentList.value = department
        }
    }

    fun isStoreMode() = mode == SELECT_MODE_STORE

    fun selectStore(position: Int) {
        storeList.resultData?.get(position)?.apply {
            selectorRepository.selectStore(this)
        }
    }

    fun selectDepartment(pos: Int) {
        departmentList.resultData?.get(pos)?.apply {
            selectorRepository.selectDepartment(this)
        }
    }

    fun checkSingleDepartment(): Boolean {
        val singleDepartment = departmentList.resultData?.size == 1
        if (singleDepartment) {
            selectorRepository.selectDepartment(departmentList.resultData!![0])
        }
        return singleDepartment
    }


}