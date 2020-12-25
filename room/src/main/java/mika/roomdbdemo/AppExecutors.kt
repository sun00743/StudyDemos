package mika.roomdbdemo

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * app 线程执行器
 */
class AppExecutors {

    companion object {
        private var INSTANCE: AppExecutors? = null

        private fun getInstance(): AppExecutors {
            if (INSTANCE == null) {
                INSTANCE = AppExecutors()
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun dbIO(): Executor = getInstance().dbIO

        @JvmStatic
        fun main(): Executor = getInstance().main
    }

    val main: Executor
    val dbIO: Executor

    init {
        dbIO = Executors.newSingleThreadExecutor()
        main = MainExecutor()
    }


    /**
     * 主线程Handler
     */
    inner class MainExecutor : Executor {

        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            command?.let {
                handler.post(it)
            }
        }

    }

}