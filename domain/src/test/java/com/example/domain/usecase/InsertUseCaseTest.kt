package com.example.domain.usecase

import android.content.Context
import com.example.domain.repository.Repository
import com.example.domain.wrapper.ResultState
import com.example.domain.wrapper.TodoItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertUseCaseTest {

    private lateinit var repository: Repository
    private lateinit var insertUseCase: InsertUseCase
    private lateinit var context: Context

    @Before
    fun setup() {
        context = mockk()
        repository = mockk(relaxed = true)
        insertUseCase = InsertUseCase(repository, context)
    }

    @Test
    fun `invoke should emit Loading and Success when repository call is successful`() =
        runBlocking {
            val todoItem = TodoItem(id = 1, item = "Test Todo")
            coEvery { repository.insertTodoItem(todoItem) } returns Unit

            val result = mutableListOf<ResultState<Unit>>()
            val flow = insertUseCase(todoItem)
            flow.toList(result)

            assertEquals(2, result.size)
            assertTrue(result[0] is ResultState.Loading)
            assertTrue(result[1] is ResultState.Success)
            coVerify { repository.insertTodoItem(todoItem) }
        }

    @Test
    fun `invoke should emit Loading and Failure when repository call throws exception`() =
        runBlocking {
            val todoItem = TodoItem(id = 1, item = "Test Todo")
            val exceptionMessage = "Something went wrong"
            coEvery { repository.insertTodoItem(todoItem) } throws Exception(exceptionMessage)

            val result = mutableListOf<ResultState<Unit>>()
            val flow = insertUseCase(todoItem)
            flow.toList(result)

            assertEquals(2, result.size)
            assertTrue(result[0] is ResultState.Loading)
            assertTrue(result[1] is ResultState.Error)
            assertEquals(exceptionMessage, (result[1] as ResultState.Error).error)
            coVerify { repository.insertTodoItem(todoItem) }
        }
}