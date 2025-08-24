package com.tishukov.planner.settings.child.auth.child.reqister.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.tishukov.planner.common.ui.atoms.AppButton
import com.tishukov.planner.common.ui.atoms.AppTextField
import com.tishukov.planner.common.ui.atoms.AppToast
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.settings.child.auth.child.reqister.RegisterViewModel
import com.tishukov.planner.settings.child.auth.child.reqister.model.RegisterContract
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun RegisterDialog(
    viewModel: RegisterViewModel,
    successListener: () -> Unit
) {

    val state = viewModel.uiState.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is RegisterContract.Event.Error -> showMessage = event.message
                RegisterContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

    Box {
        RegisterDialog(state, viewModel::onAction)

        AppToast(showMessage) { showMessage = null }
    }
}

@Composable
private fun RegisterDialog(
    state: State<RegisterContract.State>,
    onAction: (RegisterContract.Action) -> Unit,
) {

    val stateValue = state.value

    Column(
        modifier = Modifier
            .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTextField(
            stateValue.email,
            stringResource(MR.strings.email),
            onValueChange = {
                onAction(RegisterContract.Action.ChangeEmail(it))
            }
        )

        AppTextField(
            stateValue.password,
            stringResource(MR.strings.password),
            onValueChange = {
                onAction(RegisterContract.Action.ChangePassword(it))
            }
        )

        AppTextField(
            stateValue.userName,
            stringResource(MR.strings.user_name),
            onValueChange = {
                onAction(RegisterContract.Action.UserName(it))
            }
        )

        AnimatedVisibility(
            stateValue.sendIsActive,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AppButton(stringResource(MR.strings.register), onClick = {
                onAction(RegisterContract.Action.Register)
            })
        }
    }
}
