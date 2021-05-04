package com.hd123.kds.bussiness.ordermanage.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.databinding.FragmentOrderBinding
import com.hd123.kds.databinding.ItemOrderCookBinding
import com.hd123.kds.extension.dpToPx
import com.hd123.kds.model.Order
import com.hd123.kds.widget.BindingHolderAdapter

const val LOAD_DATA_TYPE_ALL = 0

/**
 * 制作中
 */
const val LOAD_DATA_TYPE_COOKING = 1

/**
 * 待取餐
 */
const val LOAD_DATA_TYPE_READY = 2

class OrderFragment : BaseFragment() {

    companion object {

        private const val LOAD_DATA_TYPE = "data_type"

        @JvmStatic
        fun newInstance(dataType: Int) = OrderFragment().apply {
            arguments = Bundle().apply {
                putInt(LOAD_DATA_TYPE, dataType)
            }
        }
    }

    private val mBinding by lazy { FragmentOrderBinding.inflate(layoutInflater) }

    private val mViewModel by viewModels<OrderViewModel>()

    private var mAdapter = OrderAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.init(arguments?.getInt(LOAD_DATA_TYPE) ?: LOAD_DATA_TYPE_ALL)
        initView()
        initObserver()
        loadData()
    }

    private fun initView() {
        mBinding.orderTitle.setText(getTitle())

        mBinding.orderList.apply {
            val spanCount = if (mViewModel.mode == LOAD_DATA_TYPE_ALL) 4 else 2
            layoutManager = GridLayoutManager(context, spanCount)
            itemAnimator = null
            adapter = mAdapter
            setPadding(dpToPx(8))
        }
    }

    private fun initObserver() {
        mViewModel.loadingFlag.observe(viewLifecycleOwner, {
            mBinding.orderLoading.visibility = if (it) View.VISIBLE else View.GONE
        })

        mViewModel.orderList.observeWithToast(viewLifecycleOwner, { data ->
            mAdapter.setData(ArrayList(data))
        } ,{

        })
    }

    private fun loadData() {
        mViewModel.loadOrder()
    }

    override fun onCurrent() {
        super.onCurrent()
        // TODO: 2021/4/30
        Log.d("mika_order", "onCurrent: " + mViewModel.mode)
    }

    private fun getTitle(): Int = when (mViewModel.mode) {
        LOAD_DATA_TYPE_COOKING -> R.string.order_manage_cooking
        LOAD_DATA_TYPE_READY -> R.string.order_manage_ready
        else -> R.string.order_manage_all
    }

    inner class OrderAdapter : BindingHolderAdapter<Order, ItemOrderCookBinding>(
            R.layout.item_order_cook, { item, bindingHolder ->
        bindingHolder.binding.apply {
            val space = if (mViewModel.mode == LOAD_DATA_TYPE_ALL) 12 else 6
            (root.layoutParams as RecyclerView.LayoutParams).setMargins(dpToPx(space))

            isOnline = item.from == Order.FROM_ONLINE
            val ready = item.cookState == Order.STATE_READY
            isReady = ready

            // TODO: 2021/4/30
//            waitTime.text = Date() - item.orderDate

            orderDate.text = String.format(getString(R.string.order_manage_order_date), item.orderDate)
            orderCode.text = item.code

            orderCookOver.setOnClickListener {
                // TODO: 2021/4/30  制作完成
            }

            orderPickUp.setOnClickListener {
                // TODO: 2021/4/30  确认取餐
            }
        }
    }) {

    }
}