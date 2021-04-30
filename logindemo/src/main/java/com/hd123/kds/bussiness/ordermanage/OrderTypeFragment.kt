package com.hd123.kds.bussiness.ordermanage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.bussiness.ordermanage.order.LOAD_DATA_TYPE_COOKING
import com.hd123.kds.bussiness.ordermanage.order.LOAD_DATA_TYPE_READY
import com.hd123.kds.bussiness.ordermanage.order.OrderFragment
import com.hd123.kds.databinding.FragmentOrderTypeBinding

class OrderTypeFragment : BaseFragment() {

    private val mBinding by lazy { FragmentOrderTypeBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().apply {
            add(R.id.order_type_cooking, OrderFragment.newInstance(LOAD_DATA_TYPE_COOKING))
            add(R.id.order_type_ready, OrderFragment.newInstance(LOAD_DATA_TYPE_READY))
            commitNow()
        }
    }

    override fun onCurrent() {
        super.onCurrent()

        childFragmentManager.fragments.forEach {
            (it as? BaseFragment)?.onCurrent()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderTypeFragment()
    }
}