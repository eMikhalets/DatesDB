package com.emikhalets.datesdb.data.database

import androidx.room.*
import com.emikhalets.datesdb.data.entities.DateType

@Dao
interface TypesDao {

    @Insert
    suspend fun insert(date: DateType): Long

    @Update
    suspend fun update(date: DateType): Int

    @Delete
    suspend fun delete(date: DateType): Int

    @Query("SELECT * FROM types_table WHERE name = :name")
    suspend fun getType(name: String): DateType

    @Query("SELECT * FROM types_table")
    suspend fun getAllTypes(): List<DateType>
}