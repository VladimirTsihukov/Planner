package com.tishukov.planner.settings.child.auth.child.sing_in.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tishukov.planner.MR
import com.tishukov.planner.common.ui.atoms.AppButton
import com.tishukov.planner.common.ui.atoms.AppTextField
import com.tishukov.planner.common.ui.atoms.AppToast
import com.tishukov.planner.common.ui.theme.AppThemeProvider
import com.tishukov.planner.settings.child.auth.child.sing_in.SignInViewModel
import com.tishukov.planner.settings.child.auth.child.sing_in.model.SignInContract
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInDialog(
    viewModel: SignInViewModel,
    successListener: () -> Unit
) {

    val showMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is SignInContract.Event.Error -> showMessage.value = event.message
                SignInContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

    SignInDialogContent(
        state = viewModel.uiState.collectAsState(),
        showMessage = showMessage,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun SignInDialogContent(
    state: State<SignInContract.State>,
    showMessage: MutableState<String?>,
    onAction: (SignInContract.Action) -> Unit,
) {
    val stateValue = state.value

    Box {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(
                AppThemeProvider.colors.surface,
                RoundedCornerShape(16.dp)
            )
                .padding(16.dp)
        ) {
            AppTextField(
                stateValue.email,
                stringResource(MR.strings.email),
                onValueChange = {
                    onAction(SignInContract.Action.ChangeEmail(it))
                }
            )

            AppTextField(
                stateValue.password,
                stringResource(MR.strings.password),
                onValueChange = {
                    onAction(SignInContract.Action.ChangePassword(it))
                }
            )

            AppButton(
                title = stringResource(MR.strings.login),
                onClick = { onAction(SignInContract.Action.Login) },
            )
        }

        AppToast(showMessage.value) { showMessage.value = null }
    }
}
