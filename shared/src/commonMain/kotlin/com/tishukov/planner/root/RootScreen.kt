package com.tishukov.planner.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tishukov.planner.common.ui.AppTheme
import com.tishukov.planner.settings.compose.SettingsScreen

@Composable
fun RootScreen(
    viewModel: RootViewModel = viewModel { RootViewModel() },
) {

    val uiStateValue = viewModel.uiState.collectAsState().value

    AppTheme(
        isThemeDark = uiStateValue.isThemeDark,
        appPrefs = uiStateValue.appPrefs,
    ) {
        SettingsScreen()
    }
}
