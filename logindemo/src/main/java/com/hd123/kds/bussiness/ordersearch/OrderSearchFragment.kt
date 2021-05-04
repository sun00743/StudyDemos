package com.hd123.kds.bussiness.ordersearch

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.databinding.FragmentOrderSearchBinding
import com.hd123.kds.databinding.ItemOrderSearchBinding
import com.hd123.kds.extension.afterTextChanged
import com.hd123.kds.model.Order
import com.hd123.kds.widget.BindingHolderAdapter
import com.hd123.kds.widget.ToastHolder

class OrderSearchFragment : BaseFragment() {

    private val mBinding by lazy {
        FragmentOrderSearchBinding.inflate(layoutInflater)
    }

    private val mViewModel by viewModels<OrderSearchViewModel>()

    private val mAdapter = OrderSearchAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView() {
        mBinding.searchList.apply {
            itemAnimator = null
            adapter = mAdapter
        }

        mBinding.searchInput.afterTextChanged {
            mBinding.doSearch.isEnabled = it.isNotBlank()
        }

        mBinding.doSearch.setOnClickListener {
            val input = mBinding.searchInput.text.trim().toString()
            if (isSearchValid(input)) {
                mViewModel.search(input)
            }
        }
    }
    private fun initObserver() {
        mViewModel.orderList.observeWithToast(viewLifecycleOwner, {
            mAdapter.setData(it.toMutableList())
        })
    }

    override fun onCurrent() {
        super.onCurrent()
    }

    private fun isSearchValid(input: String): Boolean {
        if (input.isBlank()) {
            ToastHolder.toast(R.string.order_search_error_input_empty)
            return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderSearchFragment()
    }

    inner class OrderSearchAdapter : BindingHolderAdapter<Order, ItemOrderSearchBinding>(
            R.layout.item_order_search, { item, holder ->
        holder.binding.apply {
            changeBg = holder.adapterPosition % 2 != 0
            isOnline = item.from == Order.FROM_ONLINE
            orderCode.text = item.code
            // TODO: 2021/5/4
//            orderTime.text =
            orderState.setText(getStateStr(item.cookState))
            orderState.setTextColor(requireActivity().getColor(getStateColor(item.cookState)))
        }
    })

    private fun getStateStr(cookState: Int): Int = when (cookState) {
        Order.STATE_READY -> R.string.order_state_ready
        Order.STATE_FINISH -> R.string.order_state_finish
        else -> R.string.order_state_cooking
    }

    private fun getStateColor(cookState: Int): Int = when (cookState) {
        Order.STATE_READY -> R.color.order_state_ready
        Order.STATE_FINISH -> R.color.order_state_finish
        else -> R.color.order_state_cooking
    }
}