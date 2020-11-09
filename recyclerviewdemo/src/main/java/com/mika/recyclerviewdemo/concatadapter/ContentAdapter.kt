package com.mika.recyclerviewdemo.concatadapter

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mika on 2020/10/8.
 */
class ContentAdapter(private var data: List<String>)
    : RecyclerView.Adapter<ContentAdapter.HeadViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadViewHolder =
            HeadViewHolder(TextView(parent.context).apply {
                layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180)
                gravity = Gravity.CENTER
                textSize = 20f
                setBackgroundColor(Color.WHITE)
            })

    override fun onBindViewHolder(holder: HeadViewHolder, position: Int) {
        if (position == 0) {
            Log.d("mika_recyclerView", "contentAdapter1 bind VH")
        }
        if (position == 5) {
            Log.d("mika_recyclerView", "contentAdapter1 pos$position, " +
                    "absAdapterPos: ${holder.absoluteAdapterPosition}, " +
                    "bindAdapterPos: ${holder.bindingAdapterPosition}")
        }
        val itemData = data[position]
        (holder.itemView as TextView).text = "pos: $position，data: $itemData"
    }

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class HeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}