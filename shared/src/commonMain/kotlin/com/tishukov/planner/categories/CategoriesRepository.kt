package com.tishukov.planner.categories

import com.tishukov.planner.categories.data.CategoryDao
import com.tishukov.planner.categories.models.Category
import com.tishukov.planner.extensions.appLog
import kotlinx.coroutines.flow.flow

class CategoriesRepository(
    private val dao: CategoryDao,
) {
    fun getAllFlow() = dao.getAllFlow()

    suspend fun create(category: Category) = dao.insert(category)
}
