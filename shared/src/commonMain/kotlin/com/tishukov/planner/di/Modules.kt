package com.tishukov.planner.di

import com.tishukov.planner.categories.CategoriesRepository
import com.tishukov.planner.categories.list.CategoriesViewModel
import com.tishukov.planner.platform.DeviceInfo
import com.tishukov.planner.common.ui.calendar.DatePickerViewModel
import com.tishukov.planner.root.RootViewModel
import com.tishukov.planner.settings.SettingsViewModel
import com.tishukov.planner.storage.SettingsManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

object CoreModules {
    val deviceInfo = module {
        single { DeviceInfo() }
    }
}

object StorageModule {
    val settings = module {
        single { SettingsManager(get()) }
    }
}

object RepositoryModule {
    val repository = module {
        single { CategoriesRepository() }
    }
}

val presentationModule = module {
    single { RootViewModel(settingsManager = get()) }
    single { DatePickerViewModel() }
    single { CategoriesViewModel(get()) }
    viewModel {
        SettingsViewModel(
            deviceInfo = get(),
            settingsManager = get(),
        )
    }
}
