package com.hd123.kds.extension

import android.app.Activity
import androidx.fragment.app.Fragment


fun Activity.dpToPx(dp: Int): Int = (resources.displayMetrics.density * dp).toInt()
fun Fragment.dpToPx(dp: Int): Int = (resources.displayMetrics.density * dp).toInt()