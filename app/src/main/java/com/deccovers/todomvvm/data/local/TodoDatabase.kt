package com.deccovers.todomvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntry::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao
}