package com.tishukov.planner.categories

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.tishukov.planner.categories.list.CategoriesViewModel
import com.tishukov.planner.categories.list.compose.CategoriesListView
import com.tishukov.planner.categories.list.compose.CreateCategoryView
import com.tishukov.planner.common.ui.atoms.FAB
import kotlinx.coroutines.launch

@Composable
fun BoxScope.CategoriesScreen(
    viewModel: CategoriesViewModel,
) {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            CreateCategoryView(
                isExpand = sheetState.currentValue == ModalBottomSheetValue.Expanded
            ) { data ->
                scope.launch { sheetState.hide() }
                viewModel.createCategory(data)
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        modifier = Modifier.zIndex(1f),
    ) {
        CategoriesListView(viewModel) { }
        FAB { scope.launch { sheetState.show() } }
    }
}
