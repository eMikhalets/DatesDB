package com.emikhalets.datesdb

import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.repository.ActivityRepository
import com.emikhalets.datesdb.data.repository.DateEditRepository
import com.emikhalets.datesdb.data.repository.DatesListRepository
import com.emikhalets.datesdb.viewmodel.ActivityViewModel
import com.emikhalets.datesdb.viewmodel.DateEditViewModel
import com.emikhalets.datesdb.viewmodel.DatesListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single { AppDatabase.get(get()) }
    single { AppDatabase.get(get()).typesDao }
    single { AppDatabase.get(get()).datesDao }
}

val repositoryModule = module {
    factory { ActivityRepository(get()) }
    factory { DatesListRepository(get()) }
    factory { DateEditRepository(get(), get()) }
}

val viewModelsModule = module {
    viewModel { ActivityViewModel(get()) }
    viewModel { DatesListViewModel(get()) }
    viewModel { DateEditViewModel(get()) }
}