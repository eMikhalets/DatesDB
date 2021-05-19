package com.emikhalets.datesdb.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "dates_table")
data class DateItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var date: Long,
    var typeId: Long,
    var daysLeft: Int = 0,
    var age: Int = 0,
    var imageUri: String = ""
) : Parcelable {

    @Ignore
    constructor(name: String, date: Long, typeId: Long) :
            this(0, name, date, typeId, 0, 0, "")
}