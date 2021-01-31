package com.emikhalets.datesdb

import android.app.Application
import com.emikhalets.datesdb.data.database.AppDatabase

class DatesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.create(this)
    }
}