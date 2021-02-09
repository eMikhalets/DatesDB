package com.emikhalets.datesdb.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types_table")
data class DateType(
        var name: String,
        var stringResource: Int,
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
)