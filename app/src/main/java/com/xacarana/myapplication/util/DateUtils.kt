package com.xacarana.myapplication.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE dd")
    fun weekRange(center: LocalDate): List<LocalDate> {
        val start = center.minusDays(center.dayOfWeek.value % 7L)
        return (0..6).map { start.plusDays(it.toLong()) }
    }
}
