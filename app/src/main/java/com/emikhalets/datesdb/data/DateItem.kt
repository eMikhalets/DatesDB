package com.emikhalets.datesdb.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dates_table")
class DateItem : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String
    var date: Long
    var type: String

    constructor(name: String, date: Long, type: String) {
        this.name = name
        this.date = date
        this.type = type
    }

    @Ignore
    constructor(id: Int, name: String, date: Long, type: String) {
        this.id = id
        this.name = name
        this.date = date
        this.type = type
    }
}