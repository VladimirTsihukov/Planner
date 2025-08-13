package com.tishukov.planner.categories.models

import com.tishukov.planner.platform.randomUUID
import kotlinx.datetime.LocalDateTime

data class CreateCategoryData(
    val title: String,
    val subtitle: String,
    val colorHex: String
)

fun CreateCategoryData.toCategory(dateTime: LocalDateTime) = Category(
    id = randomUUID(),
    title = title,
    description = subtitle,
    createAt = dateTime,
    updateAt = dateTime,
    colorHex = colorHex,
)
