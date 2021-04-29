package com.hd123.kds.base.data

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

    fun getErrorMsg(): String {
        if (this is Error) {
            return this.exception.message ?: ""
        }
        return ""
    }

}