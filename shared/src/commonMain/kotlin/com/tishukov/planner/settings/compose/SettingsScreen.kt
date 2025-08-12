package com.tishukov.planner.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.settings.SettingsContract
import com.tishukov.planner.settings.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen() {

    val viewModel: SettingsViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState()

    SettingsContent(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
fun SettingsContent(
    uiState: State<SettingsContract.State>,
    onAction: (SettingsContract.Action) -> Unit,
) {
    val uiStateValue = uiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppThemeProvider.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Settings ${uiStateValue.info.name} Screen",
            fontSize = 32.sp,
            color = Color.Red,
        )

        Row {
            Text(
                text = "Dark Theme",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 20.sp,
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = uiStateValue.isDarkTheme,
                onCheckedChange = { onAction(SettingsContract.Action.ChangeTheme(!uiStateValue.isDarkTheme)) },
            )
        }
    }
}
