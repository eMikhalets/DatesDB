package com.emikhalets.datesdb.utils

import java.time.LocalDateTime
import java.time.Year
import java.time.temporal.ChronoUnit

object DatesHelper {

    fun computeDaysLeft(selected: LocalDateTime): Int {
        val now = LocalDateTime.now()
        return if (now.dayOfYear > selected.dayOfYear) {
            selected.dayOfYear - now.dayOfYear + Year.of(now.year).length()
        } else {
            selected.dayOfYear - now.dayOfYear
        }
    }

    fun computeAge(selected: LocalDateTime): Int {
        val now = LocalDateTime.now()
        return ChronoUnit.YEARS.between(selected, now).toInt()
    }
}