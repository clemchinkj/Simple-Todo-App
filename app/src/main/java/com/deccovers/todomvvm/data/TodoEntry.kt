package com.deccovers.todomvvm.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "todo_table")
data class TodoEntry(

    @PrimaryKey val id: Int? = null,
    var title: String,
    var priority: Int,
    var timestamp: Long

): Parcelable