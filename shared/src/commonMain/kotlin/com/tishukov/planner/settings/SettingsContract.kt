package com.tishukov.planner.settings

import com.tishukov.planner.DeviceInfo
import com.tishukov.planner.base.BaseAction
import com.tishukov.planner.base.BaseViewState

class SettingsContract {
    data class State(
        val isDarkTheme: Boolean,
        val deviceInfo: DeviceInfo,
    ) : BaseViewState {

        companion object {
            fun getEmpty(): State {
                return State(
                    isDarkTheme = false,
                    deviceInfo = DeviceInfo(),
                )
            }
        }
    }

    sealed class Action : BaseAction {
        data class ChangeTheme(val isDarkTheme: Boolean) : Action()
    }
}
