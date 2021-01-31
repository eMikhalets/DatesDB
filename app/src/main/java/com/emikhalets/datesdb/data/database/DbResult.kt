package com.emikhalets.datesdb.data.database

sealed class DbResult<out T> {
    data class Success<out T : Any>(val result: T) : DbResult<T>()
    data class Error(val msg: String, val exception: Exception? = null) : DbResult<Nothing>()
}
