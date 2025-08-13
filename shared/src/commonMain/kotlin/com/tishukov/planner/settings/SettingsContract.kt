package com.tishukov.planner.settings

import com.tishukov.planner.platform.DeviceInfo
import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseViewState

class SettingsContract {
    data class State(
        val isDarkTheme: Boolean,
        val info: DeviceInfo,
    ) : BaseViewState {

        companion object {
            fun getEmpty(): State {
                return State(
                    isDarkTheme = false,
                    info = DeviceInfo(),
                )
            }
        }
    }

    sealed class Action : BaseAction {
        data class ChangeTheme(val isDarkTheme: Boolean) : Action()
    }
}
