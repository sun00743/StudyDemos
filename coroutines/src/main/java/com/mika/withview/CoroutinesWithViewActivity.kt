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

class CoroutinesWithViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_with_view)

        textView.text = "TextView\\nasdhjkvcvvvvvvvvvvvv"

        textView.setOnClickListener {
            GlobalWorker.stopWork()
        }

        initRecyclerView()

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
            textView.awaitNextLayout()
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
