package com.emikhalets.datesdb.data.database

import androidx.room.*
import com.emikhalets.datesdb.data.entities.DateType

@Dao
interface TypesDao {

    @Insert
    suspend fun insert(type: DateType): Long

    @Insert
    suspend fun insertAll(types: List<DateType>)

    @Update
    suspend fun update(type: DateType): Int

    @Delete
    suspend fun delete(type: DateType): Int

    @Query("SELECT * FROM types_table WHERE name = :name")
    suspend fun getType(name: String): DateType

    @Query("SELECT * FROM types_table")
    suspend fun getAllTypes(): List<DateType>
}