package com.hd123.kds.bussiness.ordermanage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.databinding.FragmentOrderManageHomeBinding
import com.hd123.kds.databinding.OrderManageAllLayoutBinding
import com.hd123.kds.databinding.OrderManageTypeLayoutBinding
import com.hd123.kds.widget.BindingHolderAdapter

const val PAGE_ALL = 0
const val PAGE_TYPE = 1

/**
 * 订单管理
 */
class OrderManageHomeFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OrderManageHomeFragment()
    }

    private val mBinding by lazy { FragmentOrderManageHomeBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        mBinding.managePager.offscreenPageLimit = 1
        mBinding.managePager.adapter = OrderManageAdapter()
        mBinding.managePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // TODO: 2021/4/29
                onCurrent()
            }
        })
    }

    private fun showPage(position: Int) {
        mBinding.managePager.currentItem = position
    }

    override fun onCurrent() {
        super.onCurrent()

        when (mBinding.managePager.currentItem) {
            PAGE_ALL -> {
                (childFragmentManager.findFragmentById(R.id.order_all) as? BaseFragment)?.onCurrent()
            }
            PAGE_TYPE -> {
                (childFragmentManager.findFragmentById(R.id.order_type) as? BaseFragment)?.onCurrent()
            }
        }
    }

    inner class OrderManageAdapter : RecyclerView.Adapter<OrderManageAdapter.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return when (viewType) {
                0 -> ViewHolder(OrderManageAllLayoutBinding.inflate(layoutInflater, parent, false).root)
                else -> ViewHolder(OrderManageTypeLayoutBinding.inflate(layoutInflater, parent, false).root)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position == 0) {
                holder.itemView.findViewById<LinearLayout>(R.id.show_type_page).setOnClickListener {
                    showPage(PAGE_TYPE)
                }
            } else {
                holder.itemView.findViewById<LinearLayout>(R.id.show_all_page).setOnClickListener {
                    showPage(PAGE_ALL)
                }
            }
        }

        override fun getItemCount(): Int = 2

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}