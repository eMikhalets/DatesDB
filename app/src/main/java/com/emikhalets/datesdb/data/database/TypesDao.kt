package com.emikhalets.datesdb.data.database

import androidx.room.*

@Dao
interface TypesDao {

    @Insert
    suspend fun insert(date: DateItem): Long

    @Update
    suspend fun update(date: DateItem): Int

    @Delete
    suspend fun delete(date: DateItem): Int

    @Query("SELECT * FROM dates_table")
    suspend fun getAllTypes(): List<DateType>
}