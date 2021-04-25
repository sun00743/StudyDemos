package com.mika.demo.study.dialogvm

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel

class DialogViewModel: ViewModel() {

    private val timer = object : CountDownTimer(60 * 1000L, 1000L) {
        override fun onFinish() {
            Log.d("mika_dialog_vm", "timer finish")
        }

        override fun onTick(millisUntilFinished: Long) {
            Log.d("mika_dialog_vm", "${this@DialogViewModel}: " +
                    " $millisUntilFinished tick: ${Thread.currentThread().name}")
        }
    }

    fun startTimer() {
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("mika_dialog_vm", "cleared")
    }

    fun use() {
        Log.d("mika_dialog_vm", "use")
    }

}