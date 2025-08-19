package com.tishukov.planner.di

import com.tishukov.planner.categories.CategoriesRepository
import com.tishukov.planner.categories.list.CategoriesViewModel
import com.tishukov.planner.common.ui.calendar.DatePickerViewModel
import com.tishukov.planner.events.EventsRepository
import com.tishukov.planner.events.ui.create.CreateEventViewModel
import com.tishukov.planner.events.ui.list.EventsViewModel
import com.tishukov.planner.platform.DeviceInfo
import com.tishukov.planner.root.RootViewModel
import com.tishukov.planner.settings.SettingsViewModel
import com.tishukov.planner.storage.SettingsManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module
import org.koin.ext.getFullName

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
        single { EventsRepository() }
    }
}

val presentationModule = module {
    single { RootViewModel(settingsManager = get()) }
    single { DatePickerViewModel() }
    single { EventsViewModel(eventsRepository = get(), categoriesRepository = get()) }
    single { DatePickerViewModel() }
    single { CreateEventViewModel() }
    single(DatePickerSingleQualifier) { DatePickerViewModel() }
    factory(DatePickerFactoryQualifier) { DatePickerViewModel() }
    single { CategoriesViewModel(get()) }
    viewModel {
        SettingsViewModel(
            deviceInfo = get(),
            settingsManager = get(),
        )
    }
}

object DatePickerSingleQualifier : Qualifier {
    override val value: QualifierValue
        get() = this::class.getFullName()
}

object DatePickerFactoryQualifier : Qualifier {
    override val value: QualifierValue
        get() = this::class.getFullName()
}
