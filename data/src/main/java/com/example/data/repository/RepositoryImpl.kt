package com.example.data.repository

import com.example.data.database.TodoDao
import com.example.data.transformation.toData
import com.example.data.transformation.toDomain
import com.example.domain.repository.Repository
import com.example.domain.wrapper.TodoItem
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : Repository {


    override suspend fun insertTodoItem(item: TodoItem) {
        dao.insert(item.toData())
    }

    override suspend fun fetchTodoItems(): List<TodoItem> =
        dao.fetch().map { item -> item.toDomain() }


}