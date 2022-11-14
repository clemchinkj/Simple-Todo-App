package com.deccovers.todomvvm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deccovers.todomvvm.data.local.TodoEntry
import com.deccovers.todomvvm.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val getAllTodos: Flow<List<TodoEntry>> = repository.getAllTodos()

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