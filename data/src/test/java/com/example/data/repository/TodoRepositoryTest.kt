package com.example.data.repository

import com.example.data.database.TodoDao
import com.example.data.database.TodoEntity
import com.example.data.transformation.toData
import com.example.domain.wrapper.TodoItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TodoRepositoryTest {


    private lateinit var todoDao: TodoDao
    private lateinit var repositoryImpl: RepositoryImpl

    @Before
    fun setUp() {
        todoDao = mockk()
        repositoryImpl = RepositoryImpl(todoDao)
    }

    @Test
    fun `insertTodoItem should call todoDao insert method`() = runBlocking {
        val todoItem = TodoItem(id = 1, item = "Test Todo")
        val todoEntity = todoItem.toData()
        coEvery { todoDao.insert(any()) } just Runs
        repositoryImpl.insertTodoItem(todoItem)
        coVerify { todoDao.insert(todoEntity) }
    }

    @Test
    fun `fetchTodoItems should return a list of TodoItems`() = runBlocking {

        val todoItem1 = TodoEntity(id = 1, item = "Test Todo")
        val todoItem2 = TodoEntity(id = 2, item = "Test Todo")
        val todoItem3 = TodoEntity(id = 3, item = "Test Todo")
        val todoItems = listOf(todoItem1, todoItem2, todoItem3)

        coEvery { todoDao.fetch() } returns todoItems

        val result = repositoryImpl.fetchTodoItems()
        assertEquals(todoItems.size, result.size)
        assertTrue(result.all { it is TodoItem })
    }
}