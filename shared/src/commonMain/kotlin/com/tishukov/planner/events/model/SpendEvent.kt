package com.tishukov.planner.events.model

import com.tishukov.planner.categories.models.Category
import com.tishukov.planner.common.ui.calendar.model.CalendarLabel
import com.tishukov.planner.extensions.now
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class SpendEvent(
    val id: String,
    val categoryId: String,
    val title: String,
    val cost: Double,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun getEmpty() = SpendEvent(
            id = "",
            categoryId = "",
            title = "",
            cost = 0.0,
            date = LocalDate.now(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        fun getStubs() = List(20) { index ->
            getEmpty().copy(
                id = index.toString(),
                title = "Spend Event $index",
                date = Clock.System.now()
                    .plus(index, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
            )
        }
    }
}

fun SpendEvent.toUI(category: Category) = SpendEventUI(
    id = id,
    category = category,
    title = title,
    cost = cost
)

fun SpendEvent.toCalendarLabel(category: Category) = CalendarLabel(
    id = id,
    colorHex = category.colorHex,
    localDate = date
)
