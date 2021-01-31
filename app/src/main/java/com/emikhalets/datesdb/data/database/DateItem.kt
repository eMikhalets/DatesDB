package com.emikhalets.datesdb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dates_table")
data class DateItem(
        val name: String,
        val date: Long,
        val type: String,
        @PrimaryKey(autoGenerate = true)
        var id: Int = -1
) : Serializable