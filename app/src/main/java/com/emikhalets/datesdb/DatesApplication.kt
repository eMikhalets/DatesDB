package com.emikhalets.datesdb

import android.app.Application
import com.emikhalets.datesdb.di.androidModule
import com.emikhalets.datesdb.di.repositoryModule
import com.emikhalets.datesdb.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DatesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DatesApplication)
            modules(androidModule, repositoryModule, viewModelsModule)
        }
    }
}