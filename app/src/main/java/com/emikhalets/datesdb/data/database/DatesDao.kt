package com.emikhalets.datesdb.data.database

import androidx.room.*
import io.reactivex.Single

@Dao
interface DatesDao {

    @Insert
    fun insert(date: DateItem)

    @Update
    fun update(date: DateItem)

    @Delete
    fun delete(date: DateItem)

    @Query("SELECT * FROM dates_table WHERE id = :id")
    fun getDate(id: Int): DateItem

    @get:Query("SELECT * FROM dates_table")
    val getAllDates: Single<List<DateItem>>
}