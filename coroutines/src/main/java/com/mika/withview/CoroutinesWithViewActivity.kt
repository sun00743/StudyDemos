package com.mika.withview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mika.coroutines.GlobalWorker
import com.mika.coroutines.R
import kotlinx.android.synthetic.main.activity_coroutines_with_view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import java.lang.Exception

class CoroutineAsync {

    var errorBlock: (Throwable) -> Unit = {}

    fun catch(errorBlock: (Throwable) -> Unit): CoroutineAsync {
        this.errorBlock = errorBlock
        return this
    }

    suspend fun <T> start(block: suspend CoroutineScope.() -> T): T {
        try {
            return withContext(Dispatchers.Default) {
                block.invoke(this)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            errorBlock.invoke(e)
            throw e
        }
    }

}

class CoroutinesWithViewActivity : AppCompatActivity() {

    private val eHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("mika_coroutine", " CoroutineAsync error: ${exception.message}")
    }

    private fun scopeLaunch(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch(eHandler) {
            block.invoke(this)
        }
    }

    fun doThings500(): String {
        Thread.sleep(500)
        return "do things 500 ok"
    }

    fun doThings1000(error: Boolean = false): String {
        Log.d("mika_coroutine", "thread: ${Thread.currentThread().name} + start do things")
        Thread.sleep(1000)
        if (error) {
            throw Exception("error!!")
        } else {
            return "do things 1000 ok"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_with_view)

        scopeLaunch {
            val value = CoroutineAsync()
                    .catch {
                        Log.d("mika_coroutine",
                                "thread: ${Thread.currentThread().name} + error: ${it.message}")
                    }
                    .start {
                        doThings1000()
                    }
            Log.d("mika_coroutine", "thread: ${Thread.currentThread().name} + value: $value")

//            cancel("cancel")

            val value1 = CoroutineAsync()
                    .catch {
                        Log.d("mika_coroutine",
                                "thread: ${Thread.currentThread().name} + error: ${it.message}")
                    }
                    .start {
                        doThings1000(true)
                    }
            Log.d("mika_coroutine", "value: $value1")

            val value2 = CoroutineAsync()
                    .catch {
                        Log.d("mika_coroutine",
                                "thread: ${Thread.currentThread().name} + error: ${it.message}")
                    }
                    .start { doThings500() }
            Log.d("mika_coroutine", "value: $value2")
        }

        textView.text = "TextView\\nasdhjkvcvvvvvvvvvvvv"

        textView.setOnClickListener {
            GlobalWorker.stopWork()
        }

        awaitViewLayout()

//        initRecyclerView()

        //等待 view layout
//        awaitViewLayout()

//        lifecycleScope.launch {
//            for (i in 0..120) {
//                delay(1000)
//                textView.text = i.toString()
//            }
//        }

        //等待动画/滚动执行完毕
//        awaitAnimator()

        //异步执行
        awaitAsyncAnimator()
    }

    private fun awaitAnimator() {
        lifecycleScope.launch {
            textView.animate().apply {
                rotation(90f)
                duration = 1000
                start()
                awaitAnimationEnd()
            }

            recyclerView.apply {
                smoothScrollToPosition(20)
                awaitScrollEnd()
            }

            ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, -100f, 0f).run {
                start()
//                awaitEnd()
            }
            textView.animate().apply {
                translationX(-100f)
                start()
                awaitAnimationEnd()
            }
        }
    }

    private fun awaitAsyncAnimator() {
        lifecycleScope.launch {
            val tAnim = async {
                textView.animate().run {
                    rotation(90f)
                    duration = 2000
                    start()
                    awaitAnimationEnd()
                }
            }

            val rAnim = async {
                delay(1000)
                recyclerView.smoothScrollToPosition(20)
                recyclerView.awaitScrollEnd()
            }

            //等待async操作完成
            tAnim.await()
            rAnim.await()

            repeat(2) {
                textView.animate().apply {
                    duration = 2000L
                    interpolator = LinearOutSlowInInterpolator()
                    translationX(200f * (it + 1))
                    start()
                    awaitAnimationEnd()
                }
            }

        }
    }

    private fun awaitViewLayout() {
        lifecycleScope.launch {
//            textView.awaitNextLayout()
//            textView.resolvesize
            Log.d("mika", "th after await : ${textView.width.toFloat()}")

            textView.translationY = -textView.width.toFloat()
            textView.animate().translationY(0f)

            textView.text = withContext(Dispatchers.IO) {
                Log.d("mika", "thread: ${Thread.currentThread().name}")
                Thread.sleep(3000)
                "result"
            }
            textView.text = withContext(Dispatchers.Default) {
                "result"
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.adapter = object : RecyclerView.Adapter<VH>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                val textView = TextView(this@CoroutinesWithViewActivity).apply {
                    layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
                    gravity = Gravity.CENTER
                }

                return VH(textView)
            }

            override fun getItemCount(): Int = 30

            override fun onBindViewHolder(holder: VH, position: Int) {
                (holder.itemView as? TextView)?.apply {
                    text = position.toString()
                }
            }

        }
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

}
