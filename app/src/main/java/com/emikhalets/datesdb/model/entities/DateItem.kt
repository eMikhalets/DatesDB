package com.emikhalets.datesdb.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dates_table")
data class DateItem(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var name: String,
        var date: Long,
        var type: String,
        var daysLeft: Int = 0,
        var age: Int = 0,
        var image: String = ""
)