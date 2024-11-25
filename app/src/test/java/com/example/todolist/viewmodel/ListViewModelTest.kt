package com.example.todolist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.usecase.FetchUseCase
import com.example.domain.wrapper.ResultState
import com.example.domain.wrapper.TodoItem
import com.example.todolist.model.ListState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `init should update todoListState with success state`() = runBlockingTest {
        val todoItem1 = TodoItem(id = 1, item = "Test Todo 1")
        val todoItem2 = TodoItem(id = 2, item = "Test Todo 2")
        val todoItems = listOf(todoItem1, todoItem2)

        val flow = flow {
            emit(ResultState.Success(todoItems))
        }

        val useCase: FetchUseCase = mockk()

        coEvery { useCase.invoke() } returns flow

        val viewModel = ListViewModel(useCase)

        advanceUntilIdle()
        val successState: ListState = viewModel.listState.value
        assertEquals(false, successState.isLoading)
        assertEquals(todoItems, successState.items)
        assertEquals("", successState.error)
    }

    @Test
    fun `init should update todoListState with failure state`() = runBlockingTest {
        val flow = flow<ResultState<List<TodoItem>>> {
            emit(ResultState.Error("Error occurred"))
        }

        val useCase: FetchUseCase = mockk()
        coEvery { useCase.invoke() } returns flow

        val viewModel = ListViewModel(useCase)

        advanceUntilIdle()
        val failureState: ListState = viewModel.listState.value
        assertEquals(false, failureState.isLoading)
        assertEquals(null, failureState.items)
        assertEquals("Error occurred", failureState.error)
    }
}