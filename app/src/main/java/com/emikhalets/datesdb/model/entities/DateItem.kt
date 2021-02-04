package com.emikhalets.datesdb.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dates_table")
data class DateItem(
        var name: String,
        var date: Long,
        var type: String,
        var daysLeft: Int,
        var age: Int,
        var isYear: Boolean,
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
)