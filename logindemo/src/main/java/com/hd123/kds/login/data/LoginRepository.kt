package com.hd123.kds.login.data

import com.hd123.kds.base.data.Result
import com.hd123.kds.model.User
import com.hd123.kds.user.UserManager
import com.hd123.kds.util.Values
import java.io.IOException


class LoginRepository {

    val dataSource: LoginDataSource = LoginDataSource()

    fun logout() {
        dataSource.logout()
    }

    fun login(username: String, password: String): Result<User> {
        val result = try {
            // TODO: handle loggedInUser authentication
            dataSource.login(username, password) //request http
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }

        if (result is Result.Success) {
            UserManager.setUser(result.data)
            Values.isLogined = true
            // TODO: 2021/4/26 after Login
        }

        return result
    }

}