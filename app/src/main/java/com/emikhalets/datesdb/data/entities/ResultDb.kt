package com.emikhalets.datesdb.data.entities

sealed class ResultDb<out T> {
    data class Success<out T>(val result: T) : ResultDb<T>()
    data class Error(val msg: String, val exception: Exception? = null) : ResultDb<Nothing>()
}
