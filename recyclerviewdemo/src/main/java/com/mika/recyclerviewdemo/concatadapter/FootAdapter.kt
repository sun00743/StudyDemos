package com.mika.recyclerviewdemo.concatadapter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mika on 2020/10/8.
 */
class FootAdapter(): RecyclerView.Adapter<FootAdapter.HeadViewHolder>() {

    override fun getItemCount(): Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadViewHolder =
            HeadViewHolder(TextView(parent.context).apply {
                layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180)
                gravity = Gravity.CENTER
                textSize = 16f
                setBackgroundColor(Color.BLUE)
            })

    override fun onBindViewHolder(holder: HeadViewHolder, position: Int) {
        (holder.itemView as TextView).text = "foot + $position"
    }

    inner class HeadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}