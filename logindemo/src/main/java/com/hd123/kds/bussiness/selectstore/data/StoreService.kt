package com.hd123.kds.bussiness.selectstore.data

import com.hd123.kds.model.BaseResponse
import com.hd123.kds.model.store.Store
import retrofit2.http.POST

interface StoreService {

    @POST("")
    fun loadStore(): BaseResponse<List<Store>>

//    @POST("")
//    fun loadDepartment(): BaseResponse<List<Store>>

}