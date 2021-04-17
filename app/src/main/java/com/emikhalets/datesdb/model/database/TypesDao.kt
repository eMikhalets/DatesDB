package com.emikhalets.datesdb.model.database

import androidx.room.*
import com.emikhalets.datesdb.model.entities.DateType

@Dao
interface TypesDao {

    @Query("SELECT * FROM types_table")
    suspend fun getAll(): List<DateType>

    @Query("SELECT * FROM types_table WHERE name = :name")
    suspend fun getItem(id: Long): DateType

    @Insert
    suspend fun insert(type: DateType): Long

    @Insert
    suspend fun insertAll(types: List<DateType>): Int

    @Update
    suspend fun update(type: DateType): Int

    @Delete
    suspend fun delete(type: DateType): Int
}