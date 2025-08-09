package com.tishukov.planner.settings

import com.tishukov.planner.DeviceInfo
import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    private val deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
) : BaseViewModel<SettingsContract.Action, SettingsContract.State, Nothing>() {

    init {
        settingsManager.themeIsDarkFlow.onEach {
            updateState { copy(isDarkTheme = it) }
        }.launchIn(viewModelScope)

        updateState {
            copy(info = deviceInfo)
        }
    }

    override fun initialState(): SettingsContract.State = SettingsContract.State.getEmpty()

    override fun onAction(action: SettingsContract.Action) {
        when (action) {
            is SettingsContract.Action.ChangeTheme -> {
                settingsManager.themeIsDark = action.isDarkTheme
            }
        }
    }
}
