package com.example.todolist.model

import com.example.domain.wrapper.TodoItem

data class ListState(
    var isLoading: Boolean = false,
    var items: List<TodoItem> = emptyList(),
    val error: String = ""
)

data class CreateState(
    var isLoading: Boolean = false,
    var status: Boolean = false,
    val error: String = ""
)
