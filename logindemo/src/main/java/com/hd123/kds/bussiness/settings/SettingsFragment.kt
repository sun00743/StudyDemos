package com.hd123.kds.bussiness.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.DialogCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hd123.kds.R
import com.hd123.kds.base.component.BaseFragment
import com.hd123.kds.databinding.ItemStoreSelectorBinding
import com.hd123.kds.databinding.SettingsFragmentBinding
import com.hd123.kds.login.ui.LoginActivity
import com.hd123.kds.model.store.Department
import com.hd123.kds.model.store.Store
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.Values
import com.hd123.kds.util.VerifyUtils
import com.hd123.kds.widget.BindingHolderAdapter
import com.hd123.kds.widget.ToastHolder

class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val mBinding by lazy { SettingsFragmentBinding.inflate(layoutInflater) }

    private val mViewModel by viewModels<SettingsViewModel>()

    private var selectedStorePos = -1
    private var selectedDepartmentPos = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        initData()
    }

    private fun initData() {
//        mViewModel.
        mBinding.tvUserName.text = UserManager.getUser().userName
        mBinding.bindLdsLayout.edtBindLds.setText(Values.LDSIP)
        mBinding.tvSwitcherStore.callOnClick()
    }

    private fun initView() {
        //选择门店
        mBinding.tvSwitcherStore.setOnClickListener {
            hideContent()
            if (mViewModel.storeList.value == null) {
                mViewModel.loadStore()
            }
            mBinding.tvSwitcherStore.isSelected = true
            mBinding.rvSwitcherStoreList.visibility = View.VISIBLE
        }
        //选择厨品部门
        mBinding.tvSwitcherDepartment.setOnClickListener {
            hideContent()
            if (mViewModel.departmentList.value == null) {
                mViewModel.loadDepartment()
            }
            mBinding.tvSwitcherDepartment.isSelected = true
            mBinding.rvSwitcherDepartmentList.visibility = View.VISIBLE
        }
        //绑定客显
        mBinding.tvBindLds.setOnClickListener {
            hideContent()
            mBinding.tvBindLds.isSelected = true
            mBinding.bindLdsLayout.root.visibility = View.VISIBLE
        }
        //确认绑定客显
        mBinding.bindLdsLayout.tvBindLdsConfirm.setOnClickListener {
            val input = mBinding.bindLdsLayout.edtBindLds.text.trim().toString()
            if (isInputValid(input)) {
                mViewModel.bindLDS(input)
            }
        }

        mBinding.tvLogout.setOnClickListener {
            logout()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mBinding.rvSwitcherStoreList.apply {
            layoutManager = GridLayoutManager(activity, 3)
            itemAnimator = null
            adapter = StoreAdapter()
        }
        mBinding.rvSwitcherDepartmentList.apply {
            layoutManager = GridLayoutManager(activity, 3)
            itemAnimator = null
            adapter = DepartmentAdapter()
        }
    }

    private fun initObserver() {
        mViewModel.loadingFlag.observe(viewLifecycleOwner, {
            mBinding.pbLoading.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        mViewModel.storeList.observeWithToast(this, {
            (mBinding.rvSwitcherStoreList.adapter as StoreAdapter).addData(it)
        })
        mViewModel.departmentList.observeWithToast(this, {
            (mBinding.rvSwitcherDepartmentList.adapter as DepartmentAdapter).addData(it)
        })
    }

    private fun isInputValid(input: String): Boolean {
        if (input.isBlank()) {
            ToastHolder.toast(getString(R.string.settings_error_blank_lds_input))
            return false
        }
        if (!VerifyUtils.verifyIP(input)) {
            ToastHolder.toast(getString(R.string.settings_error_lds_input))
            return false
        }
        return true
    }

    /**
     * 退出登录
     */
    private fun logout() {
        AlertDialog.Builder(requireContext())
                .setMessage(R.string.settings_logout_confirm)
                .setPositiveButton("确认") { dialog, i ->
                    dialog.dismiss()
                    mViewModel.logout()
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
                .setNegativeButton("取消") {dialog, i -> dialog.dismiss()}
                .create()
                .show()
    }

    private fun hideContent() {
        mBinding.tvSwitcherStore.isSelected = false
        mBinding.tvSwitcherDepartment.isSelected = false
        mBinding.tvBindLds.isSelected = false

        mBinding.rvSwitcherStoreList.visibility = View.INVISIBLE
        mBinding.rvSwitcherDepartmentList.visibility = View.INVISIBLE
        mBinding.bindLdsLayout.root.visibility = View.INVISIBLE
    }

    /**
     * 选择门店
     */
    private fun selectStore(position: Int) {
        mViewModel.selectStore(position)
        mBinding.rvSwitcherStoreList.adapter?.apply {
            notifyItemChanged(selectedStorePos)
            notifyItemChanged(position)
        }
    }

    /**
     * 选择厨品部门
     */
    private fun selectDepartment(position: Int) {
        mViewModel.selectDepartment(position)
        mBinding.rvSwitcherDepartmentList.adapter?.apply {
            notifyItemChanged(selectedDepartmentPos)
            notifyItemChanged(position)
        }
    }

    /**
     * 门店list Adapter
     */
    inner class StoreAdapter : BindingHolderAdapter<Store, ItemStoreSelectorBinding>(
            R.layout.item_store_selector, { item, holder ->
        holder.binding.apply {
            val position = holder.adapterPosition
            tvDoSelect.setOnClickListener {
                selectStore(position)
            }
            val current = item.code == mViewModel.currentStore?.code
            isCurrent = current
            if (current) {
                selectedStorePos = position
            }
            tvCode.text = item.code
            tvName.text = item.name
        }
    })

    inner class DepartmentAdapter : BindingHolderAdapter<Department, ItemStoreSelectorBinding>(
            R.layout.item_store_selector, { item, holder ->
        holder.binding.apply {
            val position = holder.adapterPosition
            tvDoSelect.setOnClickListener {
                selectDepartment(position)
            }
            val current = item.code == mViewModel.currentDepartment?.code
            isCurrent = current
            if (current) {
                selectedDepartmentPos = position
            }
            tvCode.text = item.name
            imgIcon.setImageResource(R.mipmap.cutting_bu)
            tvName.visibility = View.GONE
        }
    })


}