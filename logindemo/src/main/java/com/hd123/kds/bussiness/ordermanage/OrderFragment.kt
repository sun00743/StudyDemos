package com.hd123.kds.bussiness.ordermanage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment

const val LOAD_DATA_TYPE_ALL = 0

/**
 * 制作中
 */
const val LOAD_DATA_TYPE_COOKING = 1

/**
 * 待取餐
 */
const val LOAD_DATA_TYPE_WAITING = 2

class OrderFragment : BaseFragment() {

    companion object {

        private const val LOAD_DATA_TYPE = "data_type"

        @JvmStatic
        fun newInstance(dataType: Int) = OrderFragment().apply {
            arguments?.apply {
                putInt(LOAD_DATA_TYPE, dataType)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCurrent() {
        super.onCurrent()
    }

}