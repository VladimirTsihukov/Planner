package com.tishukov.planner.settings

import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseEvent
import com.tishukov.planner.base.BaseViewState
import com.tishukov.planner.platform.DeviceInfo

class SettingsContract {
    data class State(
        val isDarkTheme: Boolean,
        val info: DeviceInfo,
        val email: String,
        val password: String,
        val isAuth: Boolean,
        val isLoading: Boolean,
    ) : BaseViewState {

        companion object {
            fun getEmpty(): State {
                return State(
                    isDarkTheme = false,
                    info = DeviceInfo(),
                    email = "",
                    password = "",
                    isAuth = false,
                    isLoading = false,
                )
            }
        }
    }

    sealed class Action : BaseAction {
        data object Sync : Action()
        data object Logout : Action()
        data class ChangeTheme(val isDarkTheme: Boolean) : Action()
        data class SwitchTheme(val isDarkTheme: Boolean) : Action()
    }

    sealed class Event : BaseEvent {
        data object DataSynced : Event()
        data class Error(val message: String) : Event()
    }
}
