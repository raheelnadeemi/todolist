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

class FetchUseCaseTest {

    private lateinit var repository: Repository
    private lateinit var fetchUseCase: FetchUseCase
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = mockk()
        repository = mockk(relaxed = true)
        fetchUseCase = FetchUseCase(repository, context)
    }

    @Test
    fun `invoke should emit Loading and Success with a list of TodoItems when repository call is successful`() =
        runBlocking {
            val todoItem1 = TodoItem(id = 1, item = "Test Todo 1")
            val todoItem2 = TodoItem(id = 2, item = "Test Todo 2")
            val todoItems = listOf(todoItem1, todoItem2)

            coEvery { repository.fetchTodoItems() } returns todoItems

            val result = mutableListOf<ResultState<List<TodoItem>>>()
            val flow = fetchUseCase()
            flow.toList(result)

            assertEquals(2, result.size)
            assertTrue(result[0] is ResultState.Loading)
            assertTrue(result[1] is ResultState.Success)
            assertEquals(todoItems, (result[1] as ResultState.Success).data)
            coVerify { repository.fetchTodoItems() }
        }

    @Test
    fun `invoke should emit Loading and Failure when repository call throws an exception`() =
        runBlocking {
            val exceptionMessage = "Something went wrong"
            coEvery { repository.fetchTodoItems() } throws Exception(exceptionMessage)

            val result = mutableListOf<ResultState<List<TodoItem>>>()
            val flow = fetchUseCase()
            flow.toList(result)

            assertEquals(2, result.size)
            assertTrue(result[0] is ResultState.Loading)
            assertTrue(result[1] is ResultState.Error)
            assertEquals(exceptionMessage, (result[1] as ResultState.Error).error)
            coVerify { repository.fetchTodoItems() }
        }

    @Test
    fun `invoke should emit Loading and Failure with default message when repository call throws an exception without a message`() = runBlocking {
        coEvery { repository.fetchTodoItems() } throws Exception()

        val result = mutableListOf<ResultState<List<TodoItem>>>()
        val flow = fetchUseCase()
        flow.toList(result)

        assertEquals(2, result.size)
        assertTrue(result[0] is ResultState.Loading)
        assertTrue(result[1] is ResultState.Error)
        assertEquals("Something went wrong", (result[1] as ResultState.Error).error)
        coVerify { repository.fetchTodoItems() }
    }
}