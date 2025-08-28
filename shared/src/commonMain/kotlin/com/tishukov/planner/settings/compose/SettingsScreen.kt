package com.tishukov.planner.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tishukov.planner.MR
import com.tishukov.planner.common.ui.atoms.AppToast
import com.tishukov.planner.common.ui.atoms.RootBox
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.settings.SettingsContract
import com.tishukov.planner.settings.SettingsViewModel
import com.tishukov.planner.settings.child.auth.compose.AuthView
import com.tishukov.planner.settings.child.sync.compose.SyncView
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen() {

    val viewModel: SettingsViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState()
    val showMessage = remember { mutableStateOf<StringResource?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                SettingsContract.Event.DataSynced -> {
                    showMessage.value = MR.strings.data_sync_success
                }

                is SettingsContract.Event.Error -> {
                    showMessage.value = MR.strings.data_sync_error
                }
            }
        }.launchIn(this)
    }

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

    var showMessage by remember { mutableStateOf<StringResource?>(null) }

    RootBox {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {

            if (uiStateValue.isAuth) {
                SyncView(
                    email = uiStateValue.email,
                    isLoading = uiStateValue.isLoading,
                    syncListener = { onAction(SettingsContract.Action.Sync) },
                    logoutListener = { onAction(SettingsContract.Action.Logout) }
                )
            } else {
                AuthView { onAction(SettingsContract.Action.Sync) }
            }

            Row(
                modifier = Modifier.padding(16.dp)
                    .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(
                        MR.strings.dark_theme
                    ), modifier = Modifier.weight(1f),
                    color = AppThemeProvider.colors.onSurface
                )
                Switch(
                    uiStateValue.isDarkTheme,
                    onCheckedChange = { onAction(SettingsContract.Action.SwitchTheme(it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppThemeProvider.colors.accent,
                        uncheckedTrackColor = AppThemeProvider.colors.onSurface.copy(0.5f),
                        checkedTrackColor = AppThemeProvider.colors.accent.copy(0.5f),
                    )
                )
            }

            DeviceInfoView(uiStateValue.info.name)
        }

        AppToast(showMessage?.let { stringResource(it) }) { showMessage = null }
    }
}
