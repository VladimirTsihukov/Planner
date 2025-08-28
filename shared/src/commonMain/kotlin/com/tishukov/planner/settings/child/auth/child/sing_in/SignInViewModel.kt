package com.tishukov.planner.settings.child.auth.child.sing_in

import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.extensions.appLog
import com.tishukov.planner.network.ApiErrorWrapper
import com.tishukov.planner.network.AppApi
import com.tishukov.planner.settings.child.auth.child.reqister.model.AuthResponse
import com.tishukov.planner.settings.child.auth.child.sing_in.model.SignInContract.Action
import com.tishukov.planner.settings.child.auth.child.sing_in.model.SignInContract.Event
import com.tishukov.planner.settings.child.auth.child.sing_in.model.SignInContract.State
import com.tishukov.planner.settings.child.auth.child.sing_in.model.SignInRequest
import com.tishukov.planner.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(
    private val api: AppApi,
    private val settings: SettingsManager
) : BaseViewModel<Action, State, Event>() {


    override fun initialState() = State.getEmpty()

    override fun onAction(action: Action) {
        when (action) {
            is Action.ChangeEmail -> changeEmail(action.email)
            is Action.ChangePassword -> changePassword(action.pass)
            Action.Login -> login()
        }
    }

    private fun changeEmail(email: String) = updateState { copy(email = email) }

    private fun changePassword(pass: String) = updateState { copy(password = pass) }

    private fun login() {
        val signInReq = SignInRequest(
            identifier = uiState.value.email,
            password = uiState.value.password
        )
        viewModelScope.launch(
            CoroutineExceptionHandler { _, t -> pushEvent(Event.Error(t.message.orEmpty())) }
        ) {
            val response = api.singIn(signInReq)
            if (response.status.isSuccess()) {
                val signInResponse = response.body<AuthResponse>()
                settings.token = signInResponse.jwt.orEmpty()
                settings.email = uiState.value.email
                pushEvent(Event.Success)
            } else {
                val error = response.body<ApiErrorWrapper>().error
                pushEvent(Event.Error(error?.message ?: response.bodyAsText()))
                appLog(error?.message ?: response.bodyAsText())
            }
        }
    }
}
