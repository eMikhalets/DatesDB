package com.emikhalets.datesdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DateItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val datesDao: DatesDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "dates.db"
        ).build()
    }
}