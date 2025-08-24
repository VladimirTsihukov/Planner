package com.tishukov.planner.settings.child.auth.child.reqister.model

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseEvent
import com.tishukov.planner.base.BaseViewState

class RegisterContract {
    data class State(
        val email: String,
        val password: String,
        val userName: String
    ) : BaseViewState {

        val sendIsActive: Boolean
            get() = password.isNotBlank() && userName.isNotBlank() && email.isNotBlank()

        companion object {
            fun getEmpty() = State(
                email = "",
                password = "",
                userName = ""
            )
        }
    }

    sealed class Action : BaseAction {
        data class ChangeEmail(val email: String) : Action()
        data class ChangePassword(val password: String) : Action()
        data class UserName(val userName: String) : Action()
        data object Register : Action()
    }


    sealed interface Event : BaseEvent {
        data object Success : Event
        data class Error(val message: String) : Event
    }
}
