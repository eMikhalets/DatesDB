package com.emikhalets.datesdb.model.entities

sealed class AppListResult<out T : List<Any>> {
    object Loading : AppListResult<Nothing>()
    data class Success<out T : Any>(val data: T) : AppListResult<List<T>>()
    sealed class Error(val exception: Exception) : AppListResult<Nothing>() {
        object EmptyData : AppListResult<Nothing>()
        class DatabaseError(exception: Exception) : Error(exception)
    }
}