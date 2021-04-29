package com.hd123.kds.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by sunmingkai on 2021/1/28.
 */
open class BindingHolderAdapter<T, BD : ViewDataBinding>(private val layoutRes: Int,
                                                         private val onBindView: (item: T, binding: BindingViewHolder<BD>) -> Unit)
    : RecyclerView.Adapter<BindingViewHolder<BD>>() {

    var list: MutableList<T> = arrayListOf()

    fun setData(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addData(list: List<T>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<BD> {
        return BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                layoutRes, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BindingViewHolder<BD>, position: Int) {
        val item = list[position]

        onBindView.invoke(item, holder)
        holder.binding.executePendingBindings()
    }

}