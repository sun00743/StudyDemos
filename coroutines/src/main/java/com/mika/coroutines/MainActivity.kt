package com.mika.coroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mika.withview.CoroutinesWithViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    /**
     * Android协程生成器，kotlin库提供的Android UI调度器，实现了CoroutineScope协程作用域接口，
     * 可供Android在协程中更新UI。
     * 初始化了一个协程上下文和在专门在Android ui 主线程调度器
     */
    private val mainScope = MainScope()

    private var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalWorker.startWork()

        GlobalScope.launch {

        }

        click2.setOnClickListener {
//            var toInt = click2.text.toString().toInt()
//            toInt++
//            click2.text = toInt.toString()

            startActivity(Intent(this, CoroutinesWithViewActivity::class.java))
        }

        click.setOnClickListener {
            i++
            Log.d("mika_coroutines_num", "click    $i")

            //启动一个协程，默认立刻启动
            val job = mainScope.launch {
                i++
                Log.d("mika_coroutines_num", "launch前_挂起   $i")
                delay(1000)
                Log.d("mika_coroutines_num", "launch前_返回   $i")

                //协程同步访问修改资源，其顺序为代码运行顺序
                launch {
                    delay(2000)
                    i++
                    Log.d("mika_coroutines_num", "async1  " + i.toString())
                }
                launch {
                    delay(2000)
                    i++
                    Log.d("mika_coroutines_num", "async2  " + i.toString())
                }

                //协程的阻塞代码，当你想控制资源必须完成某项异步操作，其余协程才可以访问时
//                runBlocking {
//                    delay(2000)
//                    i++
//                    Log.d("mika_coroutines_num", "block  " + i.toString())
//                }

                launch {
                    async_get.text = withContext(coroutineContext) {
                        delay(2000)
                        "111"
                    }
                }
                launch {
                    async_get.text = showValue()
                }

                async_get.text = showValue()

                Log.d("mika_coroutines", "launch_挂起")
                delay(15000)
                Log.d("mika_coroutines", "launch_返回")
                async_get.text = "3"
            }
            //启动第二个协程
            mainScope.launch {
                i++
                Log.d("mika_coroutines_num", "launch后_挂起   $i")
                delay(1000)
                Log.d("mika_coroutines_num", "launch后_返回   $i")

                async_get.text = withContext(coroutineContext) {
                    delay(3000)
                    "10000000"
                }
                val async = async(Dispatchers.IO) {
                    Thread.sleep(1000)
                    i.toString()
                }
                async_get.text = async.await()

//                job.cancel()
            }

        }

        Log.d("mika_coroutines", "1")
        async_get.text = "1"

        timer()
    }

    /**
     * 挂起函数，支持协程的挂起操作、通过suspend关键字修饰，编译器也承担协程机制的实现，在launch方法中，也用suspend修饰代码块。
     */
    private suspend fun showValue(): String {
        Log.d("mika_coroutines", "showvalue_挂起")
        delay(2000)
        Log.d("mika_coroutines", "showvalue_返回")
        return "show value"
    }

    /**
     * 利用协程，可以实现一个简单的计时器
     */
    private fun timer() {
        mainScope.launch {
            for (i in 1..60) {
                delay(1000)
                async_get.text = i.toString()
            }
        }
    }

}
