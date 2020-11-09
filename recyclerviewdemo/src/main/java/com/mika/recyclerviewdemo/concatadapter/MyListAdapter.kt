package com.mika.recyclerviewdemo.concatadapter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mika on 2020/10/9.
 */
class MyListAdapter : ListAdapter<String, MyListAdapter.MyListViewHolder>(MY_STRING_DIFF_CALLBACK) {

    companion object {
        val MY_STRING_DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        return MyListViewHolder(TextView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            gravity = Gravity.CENTER
            textSize = 20f
            setTextColor(Color.GREEN)
            setBackgroundColor(Color.WHITE)
        })
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        (holder.itemView as TextView).text = "pos: $position，data: ${getItem(position)}"
    }

    override fun onCurrentListChanged(previousList: MutableList<String>, currentList: MutableList<String>) {

    }

    inner class MyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}