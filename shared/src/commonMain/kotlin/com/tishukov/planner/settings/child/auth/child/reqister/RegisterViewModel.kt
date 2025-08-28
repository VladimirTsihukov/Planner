package com.tishukov.planner.settings.child.auth.child.reqister

import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.extensions.appLog
import com.tishukov.planner.network.ApiErrorWrapper
import com.tishukov.planner.network.AppApi
import com.tishukov.planner.settings.child.auth.child.reqister.model.AuthResponse
import com.tishukov.planner.settings.child.auth.child.reqister.model.RegisterContract.Action
import com.tishukov.planner.settings.child.auth.child.reqister.model.RegisterContract.Event
import com.tishukov.planner.settings.child.auth.child.reqister.model.RegisterContract.State
import com.tishukov.planner.settings.child.auth.child.reqister.model.RegisterRequest
import com.tishukov.planner.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val api: AppApi,
    private val settingsManager: SettingsManager,
) : BaseViewModel<Action, State, Event>() {

    override fun initialState(): State = State.getEmpty()

    override fun onAction(action: Action) {
        when (action) {
            is Action.ChangeEmail -> changeEmail(action.email)
            is Action.ChangePassword -> changePassword(action.password)
            Action.Register -> register()
            is Action.UserName -> changeUserName(action.userName)
        }
    }

    private fun changeEmail(email: String) = updateState { copy(email = email) }
    private fun changePassword(pass: String) = updateState { copy(password = pass) }
    private fun changeUserName(pass: String) = updateState { copy(userName = pass) }

    fun register() {
        val registerReq = RegisterRequest(
            email = uiState.value.email,
            password = uiState.value.password,
            username = uiState.value.userName
        )
        viewModelScope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                pushEvent(Event.Error(t.message.orEmpty()))
            }
        ) {
            val response = api.register(registerReq)
            if (response.status.isSuccess()) {
                val regResponse = response.body<AuthResponse>()
                settingsManager.token = regResponse.jwt.orEmpty()
                settingsManager.email = uiState.value.email
                pushEvent(Event.Success)
            } else {
                val error = response.body<ApiErrorWrapper>().error
                pushEvent(Event.Error(error?.message ?: response.bodyAsText()))
            }
        }
    }
}
