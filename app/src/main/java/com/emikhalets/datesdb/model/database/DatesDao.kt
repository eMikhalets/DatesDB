package com.emikhalets.datesdb.model.database

import androidx.room.*
import com.emikhalets.datesdb.model.entities.DateItem

@Dao
interface DatesDao {

    @Query("SELECT * FROM dates_table ORDER BY daysLeft ASC")
    suspend fun getAll(): List<DateItem>

    @Query("SELECT * FROM dates_table WHERE id = :id")
    suspend fun getItem(id: Long): DateItem

    @Insert
    suspend fun insert(date: DateItem): Long

    @Update
    suspend fun update(date: DateItem): Int

    @Delete
    suspend fun delete(date: DateItem): Int
}