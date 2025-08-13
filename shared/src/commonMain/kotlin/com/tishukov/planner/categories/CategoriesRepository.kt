package com.tishukov.planner.categories

import com.tishukov.planner.categories.models.Category
import com.tishukov.planner.extensions.appLog
import kotlinx.coroutines.flow.flow

class CategoriesRepository {
    fun getAllFlow() = flow { emit(Category.getStubs()) }

    suspend fun create(category: Category){
        appLog("created category: $category")
    }
}
