package com.example.domain.repository

import com.example.domain.wrapper.TodoItem

interface Repository {

    suspend fun insertTodoItem(item: TodoItem)

    suspend fun fetchTodoItems(): List<TodoItem>

}