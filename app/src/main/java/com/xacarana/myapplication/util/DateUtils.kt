package com.xacarana.myapplication.util

import java.time.DayOfWeek
import java.time.LocalDate

object DateUtils {

    // La app (y el Figma) muestran la semana de Domingo a Sábado
    private val WEEK_START: DayOfWeek = DayOfWeek.SUNDAY

    /** Devuelve el primer día (domingo) de la semana que contiene [date]. */
    fun startOfWeek(date: LocalDate): LocalDate {
        var d = date
        while (d.dayOfWeek != WEEK_START) {
            d = d.minusDays(1)
        }
        return d
    }

    /** Rango de 7 días a partir del domingo de la semana de [date]. */
    fun weekRange(date: LocalDate): List<LocalDate> {
        val start = startOfWeek(date)
        return (0..6).map { start.plusDays(it.toLong()) }
    }

    /** Rango de 7 días a partir de [start] (debe ser un domingo). */
    fun weekRangeFromStart(start: LocalDate): List<LocalDate> =
        (0..6).map { start.plusDays(it.toLong()) }

    /** ¿[date] cae dentro de la semana que comienza en [start]? */
    fun isInWeek(date: LocalDate, start: LocalDate): Boolean {
        val end = start.plusDays(6)
        return !date.isBefore(start) && !date.isAfter(end)
    }

    fun previousWeekStart(start: LocalDate): LocalDate = start.minusWeeks(1)
    fun nextWeekStart(start: LocalDate): LocalDate = start.plusWeeks(1)
}
