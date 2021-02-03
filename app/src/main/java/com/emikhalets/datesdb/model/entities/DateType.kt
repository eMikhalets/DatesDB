package com.emikhalets.datesdb.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types_table")
data class DateType(
        var name: String,
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
)