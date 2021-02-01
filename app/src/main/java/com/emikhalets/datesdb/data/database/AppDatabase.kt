package com.emikhalets.datesdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DateItem::class, DateType::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val datesDao: DatesDao
    abstract val typesDao: TypesDao

    companion object {

        // TODO: temp non private instance
        @Volatile
        var instance: AppDatabase? = null

        fun create(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "dates.db"
        ).build()

        fun get(): AppDatabase = instance!!
    }
}