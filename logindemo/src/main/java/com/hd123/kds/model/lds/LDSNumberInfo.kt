package com.hd123.kds.model.lds

import com.hd123.kds.util.MyStringUtil

data class LDSNumberInfo(val values: ArrayList<String>,
                         val itemId: String = MyStringUtil.generateUuid(),
                         val online: Boolean,
                         val state: String,
                         val call: Boolean)
