package com.tishukov.planner.category

import com.tishukov.planner.extensions.now
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
                createAt = LocalDateTime.now(),
                updateAt = LocalDateTime.now(),
                colorHex = "#000000"
            )
        }
    }
}
