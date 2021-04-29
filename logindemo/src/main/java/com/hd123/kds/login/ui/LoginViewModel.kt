package com.hd123.kds.login.ui

import androidx.lifecycle.MutableLiveData
import com.hd123.kds.R
import com.hd123.kds.base.BaseViewModel
import com.hd123.kds.login.data.LoginRepository
import com.hd123.kds.base.data.Result
import com.hd123.kds.model.User
import com.hd123.kds.push.PushManager
import com.hd123.push.ali.AliPushMgr
import com.hd123.push.listener.IAliBindCallback
import com.hd123.push.model.BindResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume


class LoginViewModel : BaseViewModel() {

    private val loginRepository: LoginRepository = LoginRepository()

    val loginForm = MutableLiveData<LoginFormState>()

    val loginSuccess = MutableLiveData<Unit>()

    /**
     * 数据检查
     */
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            return
        }
        if (!isPasswordValid(password)) {
            loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            return
        }
        loginForm.value = LoginFormState(isDataValid = true)
    }

    /**
     * 登录
     */
    fun login(username: String, password: String) {
        if (isLoading) return
        isLoading = true

        coroutineLaunch {
            val result = withContext(Dispatchers.IO) {
                loginRepository.login(username, password)
            }
            if (result is Result.Error) {
                errorMsg.value = result.exception.message
                return@coroutineLaunch
            }
// TODO: 2021/4/28 use this
/*
            val bindResult = withContext(Dispatchers.IO) {
                PushManager.bindAccount()
            }
            if (!bindResult.result) {
                errorMsg.value = bindResult.message ?: "推送服务登录异常"
                return@coroutineLaunch
            }
*/

            isLoading = false
            loginSuccess.value = Unit
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length > 5
    }

}