package com.emikhalets.datesdb.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "types_table")
data class DateType(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String
) : Parcelable