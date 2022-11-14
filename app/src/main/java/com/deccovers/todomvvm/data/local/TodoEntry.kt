package com.deccovers.todomvvm.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deccovers.todomvvm.util.Constants.TODO_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TODO_TABLE)
data class TodoEntry(

    @PrimaryKey val id: Int? = null,
    var title: String,
    var priority: Int,
    var timestamp: Long

): Parcelable