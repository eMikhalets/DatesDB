package com.emikhalets.datesdb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.datesdb.data.DateItem

@Database(entities = [DateItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val datesDao: DatesDao?

    companion object {
        private var database: AppDatabase? = null
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "dates_database")
                        .build()
            }
            return database
        }
    }
}