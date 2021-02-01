package com.emikhalets.datesdb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "types_table")
data class DateType(
        val name: String,
        @PrimaryKey(autoGenerate = true)
        var id: Int = -1
) : Serializable