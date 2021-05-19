package com.emikhalets.datesdb.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "groups_table")
data class Group(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String
) : Parcelable