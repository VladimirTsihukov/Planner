package com.tishukov.planner.categories.models

import com.tishukov.planner.extensions.now
import db.categories.CategoryDb
import kotlinx.datetime.LocalDateTime

data class Category(
    val id: String,
    val title: String,
    val description: String,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime,
    val colorHex: String,
) {

    companion object {
        fun getEmpty(): Category {
            return Category(
                id = "NONE_ID",
                title = "",
                description = "",
                createAt = LocalDateTime.Companion.now(),
                updateAt = LocalDateTime.Companion.now(),
                colorHex = "#F54927"
            )
        }
    }
}

fun CategoryDb.toEntity() = Category(
    id = id,
    title = title.orEmpty(),
    description = description.orEmpty(),
    createAt = createdAt,
    updateAt = updatedAt,
    colorHex = colorHex,
)

fun Category.toDb() = CategoryDb(
    id = id,
    title = title,
    description = description,
    createdAt = createAt,
    updatedAt = updateAt,
    colorHex = colorHex,
)
