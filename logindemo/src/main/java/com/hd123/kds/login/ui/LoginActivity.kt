package com.hd123.kds.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hd123.kds.R
import com.hd123.kds.SplashActivity
import com.hd123.kds.base.component.BaseActivity
import com.hd123.kds.bussiness.home.MainActivity
import com.hd123.kds.bussiness.selectstore.StoreSelectorActivity
import com.hd123.kds.databinding.ActivityLoginBinding
import com.hd123.kds.extension.afterTextChanged
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.DateUtil
import com.hd123.kds.util.Values
import java.util.*

class LoginActivity : BaseActivity() {

    private val mViewModel by viewModels<LoginViewModel>()

    private val mBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

        initObserver()
        initListener()
        initData()
    }

    private fun initData() {
        UserManager.getUserCache()?.let {
            mBinding.edtUsername.setText(it.userName)
            mBinding.edtPassword.setText(it.pwd)
        }
        mBinding.tvCopyright.text = String.format(getString(R.string.copy_right),
                DateUtil.formatDate(Date(), DateUtil.DATE_FORMAT_YEAR))
    }

    private fun initObserver() {
        //检查数据
        mViewModel.loginForm.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            mBinding.btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                mBinding.edtUsername.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                mBinding.edtPassword.error = getString(loginState.passwordError)
            }
        })

        //登录
        mViewModel.loginSuccess.observe(this@LoginActivity, {
            // TODO: 2021/4/26 success
            if (Values.isLogined) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                StoreSelectorActivity.start(this, StoreSelectorActivity.SELECT_MODE_STORE)
            }
        })

        mViewModel.loadingFlag.observe(this, {
            mBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
        mViewModel.errorMsg.observe(this, {
            showLoginFailed(it)
        })
    }

    private fun initListener() {
        mBinding.edtUsername.afterTextChanged {
            checkLoginData()
            mBinding.usernameClear.visibility = if (it.isBlank()) View.INVISIBLE else View.VISIBLE
        }

        mBinding.edtPassword.apply {
            afterTextChanged {
                checkLoginData()
                mBinding.passwordClear.visibility = if (it.isBlank()) View.INVISIBLE else View.VISIBLE
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login()
                }
                false
            }

            mBinding.btnLogin.setOnClickListener {
                login()
            }
        }

        mBinding.usernameClear.setOnClickListener { mBinding.edtUsername.text.clear() }
        mBinding.passwordClear.setOnClickListener { mBinding.edtPassword.text.clear() }
    }

    private fun checkLoginData() {
        mViewModel.loginDataChanged(mBinding.edtUsername.text.toString(), mBinding.edtPassword.text.toString())
    }

    private fun login() {
        mViewModel.login(mBinding.edtUsername.text.toString(), mBinding.edtPassword.text.toString())
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
