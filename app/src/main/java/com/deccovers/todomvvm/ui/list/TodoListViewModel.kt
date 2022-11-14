package com.deccovers.todomvvm.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deccovers.todomvvm.data.TodoDatabase
import com.deccovers.todomvvm.data.TodoEntry
import com.deccovers.todomvvm.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoListViewModel(application: Application): AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application).todoDao
    private val repository: TodoRepository

    val getAllTodos: Flow<List<TodoEntry>>

    init {
        repository = TodoRepository(todoDao)
        getAllTodos = repository.getAllTodos()
    }

    fun insertTodo(todoEntry: TodoEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todoEntry)
        }
    }

    fun deleteTodo(todoEntry: TodoEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todoEntry)
        }
    }

    fun updateTodo(todoEntry: TodoEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todoEntry)
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodos()
        }
    }

    fun searchDatabase(searchQuery: String): Flow<List<TodoEntry>> {
        return repository.searchDatabase(searchQuery)
    }

}