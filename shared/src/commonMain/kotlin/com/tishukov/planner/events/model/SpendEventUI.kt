package com.tishukov.planner.events.model

import com.tishukov.planner.categories.models.Category

data class SpendEventUI(
    val id: String,
    val category: Category,
    val title: String,
    val cost: Double
)
