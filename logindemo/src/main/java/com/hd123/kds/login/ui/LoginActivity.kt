package com.hd123.kds.login.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hd123.kds.R
import com.hd123.kds.bussiness.home.MainActivity
import com.hd123.kds.databinding.ActivityLoginBinding
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.DateUtil
import java.util.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

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
        loginViewModel.loginForm.observe(this@LoginActivity, Observer {
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
        loginViewModel.loginSuccess.observe(this@LoginActivity, {
            // TODO: 2021/4/26 success
            startActivity(Intent(this, MainActivity::class.java))
        })

        loginViewModel.loadingFlag.observe(this, {
            mBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
        loginViewModel.errorMsg.observe(this, {
            showLoginFailed(it)
        })
    }

    private fun initListener() {
        mBinding.edtUsername.afterTextChanged {
            checkLoginData()
        }

        mBinding.edtPassword.apply {
            afterTextChanged {
                checkLoginData()
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
    }

    private fun checkLoginData() {
        loginViewModel.loginDataChanged(mBinding.edtUsername.text.toString(), mBinding.edtPassword.text.toString())
    }

    private fun login() {
        loginViewModel.login(mBinding.edtUsername.text.toString(), mBinding.edtPassword.text.toString())
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}