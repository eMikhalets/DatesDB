package com.emikhalets.datesdb.model.entities

sealed class AppResult<out T : Any> {
    object Loading : AppResult<Nothing>()
    object Complete : AppResult<Nothing>()
    data class Success<out T : Any>(val data: T) : AppResult<T>()
    sealed class Error(val exception: Exception) : AppResult<Nothing>() {
        object EmptyData : AppResult<Nothing>()
        class DatabaseError(exception: Exception) : Error(exception)
    }
}