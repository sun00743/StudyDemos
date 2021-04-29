package com.hd123.kds.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.hd123.kds.base.component.BaseFragment

abstract class BaseFragmentStateAdapter : FragmentStateAdapter {

    private val viewPager2: ViewPager2

    protected val fragments = arrayListOf<BaseFragment>()

    protected var preItemPosition = -1

    constructor(activity: FragmentActivity, viewPager2: ViewPager2) : super(activity) {
        this.viewPager2 = viewPager2
    }

    constructor(fragment: Fragment, viewPager2: ViewPager2) : super(fragment) {
        this.viewPager2 = viewPager2
    }

    private val pageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            fragments[position].onCurrent()
            ToastHolder.toast("${fragments[position].javaClass.simpleName} onCurrent")
            if (preItemPosition != -1) {
                fragments[preItemPosition].onLeave()
                preItemPosition = position
            }
        }
    }

    abstract fun initFragments(): ArrayList<BaseFragment>

    override fun getItemCount(): Int {

        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        fragments.addAll(initFragments())
        viewPager2.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        viewPager2.unregisterOnPageChangeCallback(pageChangeCallback)
    }
}