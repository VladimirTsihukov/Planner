package com.tishukov.planner.settings.child.auth.child.sing_in.model

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseEvent
import com.tishukov.planner.base.BaseViewState

class SignInContract {

    data class State(
        val email: String,
        val password: String
    ) : BaseViewState {
        companion object {
            fun getEmpty() = State(
                email = "",
                password = ""
            )
        }
    }

    sealed interface Event : BaseEvent {
        data object Success : Event
        data class Error(val message: String) : Event
    }

    sealed interface Action : BaseAction {
        data class ChangeEmail(val email: String) : Action
        data class ChangePassword(val pass: String) : Action
        data object Login : Action
    }
}
