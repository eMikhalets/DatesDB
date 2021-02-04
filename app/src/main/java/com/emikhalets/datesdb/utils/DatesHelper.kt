package com.emikhalets.datesdb.utils

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