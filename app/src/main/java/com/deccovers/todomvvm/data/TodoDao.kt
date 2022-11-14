package com.deccovers.todomvvm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntry: TodoEntry)

    @Delete
    suspend fun deleteTodo(todoEntry: TodoEntry)

    @Update
    suspend fun updateTodo(todoEntry: TodoEntry)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getTodoByID(id: Int): TodoEntry?

    @Query("SELECT * FROM todo_table ORDER BY priority ASC, timestamp DESC")
    fun getAllTodos(): Flow<List<TodoEntry>>

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery ORDER BY priority ASC, timestamp DESC")
    fun searchDatabase(searchQuery: String): Flow<List<TodoEntry>>
}