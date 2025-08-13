package com.tishukov.planner.categories.list.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tishukov.planner.categories.list.CategoriesViewModel
import com.tishukov.planner.categories.models.Category

@Composable
fun CategoriesListView(
    viewModel: CategoriesViewModel,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier.padding(bottom = 70.dp)) {
        items(state.categoties) { category ->
            CategoryItem(category) { onClick(category) }
        }
    }
}
