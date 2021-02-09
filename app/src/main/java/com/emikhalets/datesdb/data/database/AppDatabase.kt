package com.emikhalets.datesdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType

@Database(entities = [DateItem::class, DateType::class], version = 2, exportSchema = false)
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
        ).addMigrations(MIGRATION_1_2).build()

        fun get(): AppDatabase = instance!!

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE dates_table ADD COLUMN image TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}