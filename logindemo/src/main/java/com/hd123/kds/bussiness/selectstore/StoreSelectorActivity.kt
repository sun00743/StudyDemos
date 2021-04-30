package com.hd123.kds.bussiness.selectstore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseActivity
import com.hd123.kds.bussiness.home.MainActivity
import com.hd123.kds.databinding.ActivityStoreSelectorBinding
import com.hd123.kds.databinding.ItemStoreSelectorLoginBinding
import com.hd123.kds.model.store.Department
import com.hd123.kds.model.store.Store
import com.hd123.kds.widget.BindingHolderAdapter


const val EXTRA_SELECT_MODE = "mode"

class StoreSelectorActivity : BaseActivity() {

    companion object {

        const val SELECT_MODE_STORE = 0
        const val SELECT_MODE_PART = 1

        fun start(context: Context, mode: Int) {
            val intent = Intent(context, StoreSelectorActivity::class.java)
            intent.putExtra(EXTRA_SELECT_MODE, mode)
            context.startActivity(intent)
        }
    }

    private val mBinding by lazy { ActivityStoreSelectorBinding.inflate(layoutInflater) }

    private val mViewModel by viewModels<StoreSelectorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mViewModel.mode = intent.getIntExtra(EXTRA_SELECT_MODE, SELECT_MODE_STORE)
        initView()
        initObserver()
        mViewModel.loadData()
    }

    private fun initObserver() {
        if (mViewModel.mode == SELECT_MODE_STORE) {
            mViewModel.storeList.observeWithToast(this, {
                (mBinding.rvSelectorItems.adapter as StoreAdapter).addData(it)
            }, {
                // TODO: 2021/4/28
            })
        } else {
            mViewModel.departmentList.observeWithToast(this, {
                (mBinding.rvSelectorItems.adapter as DepartmentAdapter).addData(it)
            }) {

            }
        }
    }

    private fun initView() {
        mBinding.rvSelectorItems.apply {
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = null
            adapter = if (mViewModel.isStoreMode()) StoreAdapter() else DepartmentAdapter()
        }

        mBinding.tvSelectorTitle.setText(if (mViewModel.mode == SELECT_MODE_STORE)
            R.string.switcher_store else R.string.switcher_department)

        mBinding.selectorBack.setOnClickListener {
            finish()
        }
    }

    /**
     * 选择门店
     */
    private fun selectStore(pos: Int) {
        mViewModel.selectStore(pos)
        if (mViewModel.checkSingleDepartment()) {
            startMain()
        } else {
            StoreSelectorActivity.start(this, SELECT_MODE_PART)
        }
    }

    /**
     * 选择厨品部门
     */
    private fun selectDepartment(pos: Int) {
        mViewModel.selectDepartment(pos)
        startMain()
    }

    private fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    inner class StoreAdapter : BindingHolderAdapter<Store, ItemStoreSelectorLoginBinding>(
            R.layout.item_store_selector_login, { item, holder ->
        holder.binding.apply {
            tvDoSelect.setOnClickListener {
                selectStore(holder.adapterPosition)
            }

            tvCode.text = item.code
            tvName.text = item.name
        }
    })

    inner class DepartmentAdapter : BindingHolderAdapter<Department, ItemStoreSelectorLoginBinding>(
            R.layout.item_store_selector_login, { item, holder ->
        holder.binding.apply {
            tvDoSelect.setOnClickListener {
                selectDepartment(holder.adapterPosition)
            }
            tvName.visibility = View.GONE
            tvCode.text = item.name
            imgIcon.setImageResource(R.mipmap.cutting_bu)
        }
    })
}