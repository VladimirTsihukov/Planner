package com.tishukov.planner.di

import com.tishukov.planner.categories.CategoriesRepository
import com.tishukov.planner.categories.data.CategoryDao
import com.tishukov.planner.categories.list.CategoriesViewModel
import com.tishukov.planner.common.ui.calendar.DatePickerViewModel
import com.tishukov.planner.db.AppDb
import com.tishukov.planner.events.EventsRepository
import com.tishukov.planner.events.data.SpendEventDao
import com.tishukov.planner.events.ui.create.CreateEventViewModel
import com.tishukov.planner.events.ui.list.EventsViewModel
import com.tishukov.planner.extensions.appLog
import com.tishukov.planner.network.AppApi
import com.tishukov.planner.network.DateSerializer
import com.tishukov.planner.network.DateTimeSerializer
import com.tishukov.planner.platform.DeviceInfo
import com.tishukov.planner.root.RootViewModel
import com.tishukov.planner.settings.SettingsViewModel
import com.tishukov.planner.settings.child.auth.child.reqister.RegisterViewModel
import com.tishukov.planner.settings.child.auth.child.sing_in.SignInViewModel
import com.tishukov.planner.storage.DbAdapter
import com.tishukov.planner.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
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

    val coroutineScope = module {
        single { Dispatchers.Default + SupervisorJob() }
    }
}

object NetworkModule {
    val json = module {
        single {
            Json {
                encodeDefaults = false
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = true
                serializersModule = SerializersModule {
                    contextual(LocalDateTime::class, DateTimeSerializer)
                    contextual(LocalDate::class, DateSerializer)
                }
            }
        }
    }

    val httpClient = module {
        single {
            HttpClient(CIO) {
                expectSuccess = false
                install(ContentNegotiation) {
                    json(get())
                }

                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLog(message)
                        }
                    }
                }
            }
        }
    }

    val api = module {
        single { AppApi(httpClient = get(), settings = get()) }
    }
}

object StorageModule {
    val settings = module {
        single { SettingsManager(get()) }
    }
    val db = module {
        single {
            AppDb(
                driver = get(),
                CategoryDbAdapter = DbAdapter.categoryDbAdapter,
                EventDbAdapter = DbAdapter.eventDbAdapter,
            )
        }
    }

    val dao = module {
        single { CategoryDao(db = get(), coroutineContext = get()) }
        single { SpendEventDao(db = get(), coroutineContext = get()) }
    }
}

object RepositoryModule {
    val repository = module {
        single { CategoriesRepository(dao = get()) }
        single { EventsRepository(dao = get()) }
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
            api = get(),
            categoriesRepository = get(),
            eventsRepository = get(),
            deviceInfo = get(),
            settingsManager = get(),
        )
    }
    viewModel {
        RegisterViewModel(
            api = get(),
            settingsManager = get(),
        )
    }
    viewModel {
        SignInViewModel(
            api = get(),
            settings = get(),
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
