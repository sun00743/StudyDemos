package com.hd123.kds.bussiness.settings.data

import com.hd123.kds.model.lds.BaseLDSResponse
import com.hd123.kds.model.lds.LDSNumberInfo
import retrofit2.http.POST

interface LDSService {

    @POST("/callnumber")
    fun sendOrderToLDS() : BaseLDSResponse<List<LDSNumberInfo>>

}