package com.emikhalets.datesdb.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types_table")
data class DateType(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var name: String
)