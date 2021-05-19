package com.emikhalets.datesdb.model.database

import androidx.room.*
import com.emikhalets.datesdb.model.entities.Group

@Dao
interface GroupsDao {

    @Query("SELECT * FROM groups_table")
    suspend fun getAll(): List<Group>

    @Query("SELECT * FROM groups_table WHERE name = :name")
    suspend fun getItem(name: String): Group

    @Query("SELECT EXISTS (SELECT 1 FROM groups_table WHERE name = :name) ")
    suspend fun isExist(name: String): Boolean

    @Insert
    suspend fun insert(group: Group): Long

    @Insert
    suspend fun insertAll(groups: List<Group>): Int

    @Update
    suspend fun update(group: Group): Int

    @Delete
    suspend fun delete(group: Group): Int
}