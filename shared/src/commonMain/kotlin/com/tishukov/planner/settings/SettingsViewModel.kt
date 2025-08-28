package com.tishukov.planner.settings

import com.tishukov.planner.base.BaseViewModel
import com.tishukov.planner.categories.CategoriesRepository
import com.tishukov.planner.categories.models.CategoryApi
import com.tishukov.planner.categories.models.toApi
import com.tishukov.planner.categories.models.toEntity
import com.tishukov.planner.events.EventsRepository
import com.tishukov.planner.events.model.SpendEventApi
import com.tishukov.planner.events.model.toApi
import com.tishukov.planner.events.model.toEntity
import com.tishukov.planner.extensions.appLog
import com.tishukov.planner.network.AppApi
import com.tishukov.planner.platform.DeviceInfo
import com.tishukov.planner.settings.SettingsContract.Action
import com.tishukov.planner.settings.SettingsContract.Event
import com.tishukov.planner.settings.SettingsContract.State
import com.tishukov.planner.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val api: AppApi,
    private val categoriesRepository: CategoriesRepository,
    private val deviceInfo: DeviceInfo,
    private val eventsRepository: EventsRepository,
    private val settingsManager: SettingsManager,
) : BaseViewModel<Action, State, Event>() {

    init {
        bindToEmail()
        bindToTheme()
        bindToToken()
        initDeviceInfo()
    }

    override fun initialState(): State = State.getEmpty()

    override fun onAction(action: Action) {
        when (action) {
            is Action.ChangeTheme -> {
                settingsManager.themeIsDark = action.isDarkTheme
            }

            Action.Logout -> logout()
            Action.Sync -> sync()
            is Action.SwitchTheme -> {
                settingsManager.themeIsDark = action.isDarkTheme
            }
        }
    }

    fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    private fun sync() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, t ->
                appLog(t.stackTraceToString())
                pushEvent(Event.Error(t.message.orEmpty()))
            }
        ) {
            updateState { copy(isLoading = true) }
            delay(3000)
            syncCategories()
            syncEvens()
            pushEvent(Event.DataSynced)
            updateState { copy(isLoading = false) }
        }
    }

    private suspend fun syncCategories() {
        val apiCategories = categoriesRepository.getAll().map { it.toApi() }
        val categoriesSyncResponse = api.syncCategories(apiCategories)
        if (categoriesSyncResponse.status.isSuccess()) {
            val remoteCategories = categoriesSyncResponse.body<List<CategoryApi>>()
            categoriesRepository.insertAll(remoteCategories.map(CategoryApi::toEntity))
        }
    }

    private suspend fun syncEvens() {
        val apiEvents = eventsRepository.getAll().map { it.toApi() }
        val eventsSyncResponse = api.syncEvents(apiEvents)
        if (eventsSyncResponse.status.isSuccess()) {
            val remoteEvents = eventsSyncResponse.body<List<SpendEventApi>>()
            eventsRepository.insertAll(remoteEvents.map { it.toEntity() })
        }
    }

    private fun bindToEmail() {
        settingsManager.emailFlow.onEach { email ->
            updateState { copy(email = email) }
        }.launchIn(viewModelScope)
    }

    private fun bindToToken() {
        settingsManager.tokenFlow.onEach { token ->
            updateState { copy(isAuth = token.isNotBlank()) }
        }.launchIn(viewModelScope)
    }

    private fun initDeviceInfo() {
        updateState {
            copy(info = deviceInfo)
        }
    }

    private fun bindToTheme() {
        settingsManager.themeIsDarkFlow.onEach {
            updateState { copy(isDarkTheme = it) }
        }.launchIn(viewModelScope)
    }
}
