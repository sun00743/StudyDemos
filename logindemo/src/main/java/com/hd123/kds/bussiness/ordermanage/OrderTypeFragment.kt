package com.hd123.kds.bussiness.ordermanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.databinding.FragmentOrderManageHomeBinding
import com.hd123.kds.databinding.FragmentOrderTypeBinding
import com.hd123.kds.widget.BaseFragmentStateAdapter

class OrderTypeFragment : BaseFragment() {

    private val mBinding by lazy { FragmentOrderTypeBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.typePager.adapter = object : BaseFragmentStateAdapter(this, mBinding.typePager) {
            override fun initFragments(): ArrayList<BaseFragment> =
                    arrayListOf(
                            OrderFragment.newInstance(LOAD_DATA_TYPE_COOKING),
                            OrderFragment.newInstance(LOAD_DATA_TYPE_WAITING)
                    )
        }
    }

    override fun onCurrent() {
        super.onCurrent()
        // TODO: 2021/4/29
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderTypeFragment()
    }
}