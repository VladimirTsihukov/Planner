package com.tishukov.planner.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.tishukov.planner.categories.CategoriesScreen
import com.tishukov.planner.common.ui.theme.AppTheme
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.events.EventsScreen
import com.tishukov.planner.root.RootViewModel
import com.tishukov.planner.root.models.AppTab
import com.tishukov.planner.root.models.RootContract
import com.tishukov.planner.settings.compose.SettingsScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootScreen() {

    val viewModel: RootViewModel = koinViewModel()
    val uiStateValue = viewModel.uiState.collectAsState().value

    AppTheme(
        isThemeDark = uiStateValue.isThemeDark,
        appPrefs = uiStateValue.appPrefs,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppThemeProvider.colors.background)
        ) {
            RootNavigation(selectedTab = uiStateValue.selectedTab)

            RootBottomBar(selectedTab = uiStateValue.selectedTab) {
                viewModel.onAction(RootContract.Action.SelectTab(it))
            }
        }
    }
}

@Composable
private fun BoxScope.RootNavigation(
    selectedTab: AppTab,
) {
    when (selectedTab) {
        AppTab.Categories -> CategoriesScreen()
        AppTab.Events -> EventsScreen()
        AppTab.Settings -> SettingsScreen()
    }
}
