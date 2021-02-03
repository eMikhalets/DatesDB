package com.emikhalets.datesdb.model.database

import androidx.room.*
import com.emikhalets.datesdb.model.entities.DateType

@Dao
interface TypesDao {

    @Insert
    suspend fun insert(date: DateType): Long

    @Update
    suspend fun update(date: DateType): Int

    @Delete
    suspend fun delete(date: DateType): Int

    @Query("SELECT * FROM types_table")
    suspend fun getAllTypes(): List<DateType>
}