package com.emikhalets.datesdb.data.database

import androidx.room.*

@Dao
interface DatesDao {

    @Insert
    suspend fun insert(date: DateItem): Long

    @Update
    suspend fun update(date: DateItem): Int

    @Delete
    suspend fun delete(date: DateItem): Int

    @Query("SELECT * FROM dates_table WHERE id = :id")
    suspend fun getDate(id: Int): DateItem

    @Query("SELECT * FROM dates_table")
    suspend fun getAllDates(): List<DateItem>
}