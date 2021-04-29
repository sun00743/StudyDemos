package com.hd123.kds.bussiness.ordersearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.databinding.FragmentOrderManageHomeBinding
import com.hd123.kds.databinding.FragmentOrderSearchBinding

class OrderSearchFragment : BaseFragment() {

    private val mBinding by lazy {
        FragmentOrderSearchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return mBinding.root
    }

    override fun onCurrent() {
        super.onCurrent()

    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderSearchFragment()
    }
}