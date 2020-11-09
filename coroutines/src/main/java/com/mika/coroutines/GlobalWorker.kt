package com.mika.coroutines

import android.util.Log
import kotlinx.coroutines.*

/**
 * Created by mika on 2020/9/22.
 */
object GlobalWorker {

    val mainScope: CoroutineScope by lazy {
        MainScope()
    }

    private var count = 0

    private var countJob: Job? = null

    fun startWork() {
        if (countJob != null) return

        countJob = mainScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                count++
                Log.d("mika", "count value: $count , on ${Thread.currentThread().name}")
            }
        }
    }

    fun stopWork() {
        if (countJob == null) return

        if (countJob!!.isActive) {
            countJob!!.cancel()
        }
        count = 0
        countJob = null
        Log.d("mika", "work stop, count value: $count , on ${Thread.currentThread().name}")
    }

}