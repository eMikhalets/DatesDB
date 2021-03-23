package com.emikhalets.datesdb.data.database

import androidx.room.*
import com.emikhalets.datesdb.data.entities.DateItem

@Dao
interface DatesDao {

    @Insert
    suspend fun insert(date: DateItem): Long

    @Update
    suspend fun update(date: DateItem): Int

    @Delete
    suspend fun delete(date: DateItem): Int

    @Query("SELECT * FROM dates_table WHERE id = :id")
    suspend fun getDate(id: Long): DateItem

    @Query("SELECT * FROM dates_table ORDER BY daysLeft ASC")
    suspend fun getAllDates(): List<DateItem>
}