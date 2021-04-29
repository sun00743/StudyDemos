package com.hd123.kds.bussiness.home

import android.os.Bundle
import com.hd123.kds.base.component.BaseActivity
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.bussiness.ordermanage.OrderManageHomeFragment
import com.hd123.kds.bussiness.ordersearch.OrderSearchFragment
import com.hd123.kds.bussiness.settings.SettingsFragment
import com.hd123.kds.databinding.ActivityMainBinding
import com.hd123.kds.widget.BaseFragmentStateAdapter

const val MAIN_PAGE_ORDER_MANAGE = 0
const val MAIN_PAGE_ORDER_SEARCH = 1
const val MAIN_PAGE_ORDER_SETTINGS = 2

class MainActivity : BaseActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        initPagerAdapter()
        initListener()
    }

    private fun initListener() {
        mBinding.tvTabOrderManage.setOnClickListener { showPage(MAIN_PAGE_ORDER_MANAGE) }
        mBinding.tvTabOrderSearch.setOnClickListener { showPage(MAIN_PAGE_ORDER_SEARCH) }
        mBinding.tvMainSettings.setOnClickListener { showPage(MAIN_PAGE_ORDER_SETTINGS) }
    }

    fun showPage(pos: Int) {
        mBinding.mainPager.setCurrentItem(pos, false)
    }

    private fun initPagerAdapter() {
        mBinding.mainPager.apply {
            isUserInputEnabled = false
            adapter = object : BaseFragmentStateAdapter(this@MainActivity, this) {
                override fun initFragments(): ArrayList<BaseFragment> =
                        arrayListOf(
                                OrderManageHomeFragment.newInstance(),
                                OrderSearchFragment.newInstance(),
                                SettingsFragment.newInstance()
                        )
            }
        }
    }
}