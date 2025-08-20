package com.tishukov.planner.storage

import app.cash.sqldelight.ColumnAdapter
import db.categories.CategoryDb
import db.events.EventDb
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

object DbAdapter {
    val categoryDbAdapter = CategoryDb.Adapter(
        createdAtAdapter = LocalDateTimeAdapter,
        updatedAtAdapter = LocalDateTimeAdapter,
    )

    val eventDbAdapter = EventDb.Adapter(
        dateAdapter = LocalDateAdapter,
        createdAtAdapter = LocalDateTimeAdapter,
        updatedAtAdapter = LocalDateTimeAdapter,
    )
}

object LocalDateTimeAdapter : ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime = LocalDateTime.parse(databaseValue)

    override fun encode(value: LocalDateTime): String = value.toString()

}

object LocalDateAdapter : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String): LocalDate = LocalDate.parse(databaseValue)

    override fun encode(value: LocalDate): String = value.toString()
}
