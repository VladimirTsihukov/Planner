package com.tishukov.planner.categories.models

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
                createAt = LocalDateTime.Companion.now(),
                updateAt = LocalDateTime.Companion.now(),
                colorHex = "#F54927"
            )
        }

        fun getStubs() = List(10) { index ->
            getEmpty().copy(id = index.toString(), title = "category $index")
        }
    }
}
