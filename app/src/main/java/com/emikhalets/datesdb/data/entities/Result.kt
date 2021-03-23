package com.emikhalets.datesdb.data.entities

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val result: T) : Result<T>()
    data class Error(val msg: String, val exception: Exception? = null) : Result<Nothing>()
}
