package com.tishukov.planner.common.ui.calendar.extensions

import kotlinx.datetime.DayOfWeek

fun Array<DayOfWeek>.fromSunday(): List<DayOfWeek> {
    val valuesFromSunday = DayOfWeek.entries.toMutableList().apply {
        remove(DayOfWeek.SUNDAY)
    }
    return listOf(DayOfWeek.SUNDAY) + valuesFromSunday
}

