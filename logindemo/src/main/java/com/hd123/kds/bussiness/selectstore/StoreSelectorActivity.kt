package com.hd123.kds.bussiness.selectstore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseActivity
import com.hd123.kds.bussiness.home.MainActivity
import com.hd123.kds.databinding.ActivityStoreSelectorBinding
import com.hd123.kds.databinding.ItemStoreSelectorBinding
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
        mViewModel.storeList.observeWithToast(this, {
            (mBinding.rvSelectorItems.adapter as StoreAdapter).addData(it)
        }, {
            // TODO: 2021/4/28
        })
    }

    private fun initView() {
        mBinding.rvSelectorItems.apply {
            layoutManager = GridLayoutManager(this@StoreSelectorActivity, 3)
            itemAnimator = null
            adapter = if (mViewModel.isStoreMode()) StoreAdapter() else DepartmentAdapter()
        }
    }

    /**
     * 选择门店
     */
    private fun selectStore(pos: Int) {
        mViewModel.selectStore(pos)
        if (mViewModel.checkSingleDepartment()) {
             startActivity(Intent(this, MainActivity::class.java))
        } else {
            StoreSelectorActivity.start(this, SELECT_MODE_PART)
        }
    }

    /**
     * 选择厨品部门
     */
    private fun selectDepartment(pos: Int) {
        mViewModel.selectDepartment(pos)
        startActivity(Intent(this, MainActivity::class.java))
    }

    inner class StoreAdapter : BindingHolderAdapter<Store, ItemStoreSelectorLoginBinding>(
            R.layout.item_store_selector_login, { item, holder ->
        holder.binding.apply {
            tvDoSelect.setOnClickListener {
                selectStore(holder.adapterPosition)
            }

            tvCode.text = item.code
            tvName.text = item.name
            executePendingBindings()
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
            executePendingBindings()
        }
    })
}