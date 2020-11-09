package com.mika.recyclerviewdemo.concatadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mika.recyclerviewdemo.R
import kotlinx.android.synthetic.main.activity_concat_adapter.*

class ConcatAdapterActivity : AppCompatActivity() {

    private val headAdapter1 = HeadAdapter()

    private val contentAdapter1 = ContentAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concat_adapter)

        setAdapter()

        contentAdapter1.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                Log.d("mika_recyclerView", "contentAdapter1 changed")
            }
        })
    }

    private fun setAdapter() {
//        ConcatAdapter.Config.Builder()
//                .build()
        val data = generateData()
        contentAdapter1.setData(data)
        val listAdapter = MyListAdapter()

        val cAdapter = ConcatAdapter(headAdapter1,
                contentAdapter1,
                HeadAdapter(),
                listAdapter,
                FootAdapter())

        cAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                Log.d("mika_recyclerView", "cAdapter changed")
            }
        })
        recyclerView.adapter = cAdapter
        listAdapter.submitList(data)

        Handler(Looper.getMainLooper()).postDelayed({
//            Log.d("mika_recyclerView", "set contentAdapter1 data2")
            contentAdapter1.setData(generateData2())
//            cAdapter.removeAdapter(headAdapter1)

            listAdapter.submitList(generateData2())
        }, 3000)
    }

    private fun generateData() : ArrayList<String> {
        val data = arrayListOf<String>()
        for (i in 0 until 15) {
            data.add("Mika$i")
        }
        return data
    }

    private fun generateData2() : ArrayList<String> {
        val data = arrayListOf<String>()
        for (i in 15 until 40) {
            data.add("Mika$i")
        }
        return data
    }

}
