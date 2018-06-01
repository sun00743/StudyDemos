package com.mika.demo.study.livedata.extend

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by mika on 2018/6/1
 */
@Parcelize
data class BigDecimal(
        val num: Int)
    : Parcelable