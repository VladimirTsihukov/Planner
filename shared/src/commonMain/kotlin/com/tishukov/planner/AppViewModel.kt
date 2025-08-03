package com.tishukov.planner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel : ViewModel() {
    private val deviceInfo = DeviceInfo()
    private var counter = 0

    private val _uiState = MutableStateFlow("")
    val uiState = _uiState

    init {
        _uiState.value = "Initialized with ${deviceInfo.name}}"
    }

    fun onClick() {
        _uiState.value += "\nClicked ${counter++}"
    }
}

