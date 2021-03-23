package com.emikhalets.datesdb.utils

import com.emikhalets.datesdb.data.entities.Result
import java.time.LocalDateTime
import java.time.Year
import java.time.temporal.ChronoUnit

fun computeDaysLeft(selected: LocalDateTime): Int {
    val now = LocalDateTime.now()
    val date = LocalDateTime.from(selected).withYear(now.year)
    return if (now.dayOfYear > date.dayOfYear) {
        date.dayOfYear - now.dayOfYear + Year.of(now.year).length()
    } else {
        date.dayOfYear - now.dayOfYear
    }
}

fun computeAge(selected: LocalDateTime): Int {
    val now = LocalDateTime.now()
    return ChronoUnit.YEARS.between(selected, now).toInt()
}

suspend fun <T: Any> safeDataSourceCall(call: suspend () -> T): Result<T> =
        try {
            val result = call.invoke()
            Result.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(ex.message ?: "Error: no message", ex)
        }