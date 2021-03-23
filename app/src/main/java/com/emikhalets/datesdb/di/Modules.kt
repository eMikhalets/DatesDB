package com.emikhalets.datesdb.di

import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.repository.ActivityRepository
import com.emikhalets.datesdb.data.repository.DatesListRepository
import com.emikhalets.datesdb.viewmodel.ActivityViewModel
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
    factory { DatesListRepository(get(), get()) }
}

val viewModelsModule = module {
    viewModel { ActivityViewModel(get()) }
    viewModel { DatesListViewModel(get()) }
}