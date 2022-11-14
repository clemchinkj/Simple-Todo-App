package com.deccovers.todomvvm.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun insertTodo(todoEntry: TodoEntry) = todoDao.insertTodo(todoEntry)

    suspend fun deleteTodo(todoEntry: TodoEntry) = todoDao.deleteTodo(todoEntry)

    suspend fun updateTodo(todoEntry: TodoEntry) = todoDao.updateTodo(todoEntry)

    suspend fun deleteAllTodos() {
        todoDao.deleteAll()
    }

    fun getAllTodos(): Flow<List<TodoEntry>> = todoDao.getAllTodos()

    fun searchDatabase(searchQuery: String): Flow<List<TodoEntry>> {
        return todoDao.searchDatabase(searchQuery)
    }
}