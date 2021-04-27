package com.hd123.kds.login.data

import com.hd123.kds.base.data.Result
import com.hd123.kds.model.User

class LoginDataSource {

    fun login(username: String, password: String): Result<User> {
        val fakeUser = User(username, password, "fakeToken")
        return Result.Success(fakeUser)
    }

    fun logout() {
        // TODO: revoke authentication
    }
}