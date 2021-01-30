package com.emikhalets.datesdb.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DateItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DatesDao getDatesDao();

    private static AppDatabase database;

    public static synchronized AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "dates_database")
                    .build();
        }
        return database;
    }
}