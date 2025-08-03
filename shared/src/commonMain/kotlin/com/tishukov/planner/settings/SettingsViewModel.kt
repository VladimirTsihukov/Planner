package com.tishukov.planner.settings

import com.tishukov.planner.DeviceInfo
import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsViewModel : BaseViewModel<SettingsContract.Action, SettingsContract.State, Nothing>() {

    init {
        SettingsManager.themeIsDarkFlow.onEach {
            updateState { copy(isDarkTheme = it) }
        }.launchIn(viewModelScope)

        updateState {
            copy(deviceInfo = DeviceInfo())
        }
    }

    override fun initialState(): SettingsContract.State = SettingsContract.State.getEmpty()

    override fun onAction(action: SettingsContract.Action) {
        when (action) {
            is SettingsContract.Action.ChangeTheme -> {
                SettingsManager.themeIsDark = action.isDarkTheme
            }
        }
    }
}
