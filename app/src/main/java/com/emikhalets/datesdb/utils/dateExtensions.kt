package com.emikhalets.datesdb.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//private fun formatDate(timestamp: Long): String {
//    val date = LocalDateTime.ofInstant(
//            Instant.ofEpochMilli(timestamp),
//            ZoneId.of("UTC")
//    )
//    return date.format(DateTimeFormatter.ofPattern("d MMM y, E"))
//}
//
//private fun formatDateWithoutYear(timestamp: Long): String {
//    val date = LocalDateTime.ofInstant(
//            Instant.ofEpochMilli(timestamp),
//            ZoneId.of("UTC")
//    ).withYear(LocalDateTime.now().year + 1)
//    return date.format(DateTimeFormatter.ofPattern("d MMM, E"))
//}

//private fun List<DateItem>.updateDatesData() {
//    for (i in this.indices) {
//        val selected = LocalDateTime.ofInstant(
//                Instant.ofEpochMilli(list[i].date),
//                ZoneId.of("UTC")
//        )
//        val oldDaysLeft = this[i].daysLeft
//        val oldAge = this[i].age
//        this[i].apply {
//            daysLeft = computeDaysLeft(selected)
//            if (this[i].isYear) this[i].age = computeAge(selected)
//        }
//
//        if (oldDaysLeft != this[i].daysLeft || oldAge != this[i].age) {
//            viewModelScope.launch { repository.update(this[i]) }
//        }
//    }
//}

//fun setLocalDateTime(day: Int, month: Int, year: Int): LocalDateTime {
//    return LocalDateTime.now()
//            .withYear(year)
//            .withMonth(month + 1)
//            .withDayOfMonth(day)
//}
//
//fun computeDaysLeft(selected: LocalDateTime): Int {
//    val now = LocalDateTime.now()
//    val date = LocalDateTime.from(selected).withYear(now.year)
//    return if (now.dayOfYear > date.dayOfYear) {
//        date.dayOfYear - now.dayOfYear + Year.of(now.year).length()
//    } else {
//        date.dayOfYear - now.dayOfYear
//    }
//}

//fun computeAge(selected: LocalDateTime): Int {
//    val now = LocalDateTime.now()
//    return ChronoUnit.YEARS.between(selected, now).toInt()
//}
//
//
//
//private fun formatDate(timestamp: Long): String {
//    val date = LocalDateTime.ofInstant(
//            Instant.ofEpochMilli(timestamp),
//            ZoneId.of("UTC")
//    )
//    return date.format(DateTimeFormatter.ofPattern("d MMM y, E"))
//}
//
//private fun formatDateWithoutYear(timestamp: Long): String {
//    val date = LocalDateTime.ofInstant(
//            Instant.ofEpochMilli(timestamp),
//            ZoneId.of("UTC")
//    ).withYear(LocalDateTime.now().year + 1)
//    return date.format(DateTimeFormatter.ofPattern("d MMM, E"))
//}