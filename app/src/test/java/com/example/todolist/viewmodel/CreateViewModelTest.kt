package com.example.todolist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.usecase.InsertUseCase
import com.example.domain.wrapper.ResultState
import com.example.todolist.model.CreateState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CreateViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `insertItem should update createState with success state`() = runBlocking {
        val insertUseCase: InsertUseCase = mockk()

        coEvery { insertUseCase.invoke(any()) } returns flow {
            emit(ResultState.Success(Unit))
        }

        val viewModel = CreateViewModel(insertUseCase)
        viewModel.insertItem("Test Todo")

        val successState: CreateState = viewModel.createState.value
        assertEquals(false, successState.isLoading)
        assertEquals("", successState.error)
        assertEquals(true, successState.status)
    }

    @Test
    fun `insertItem should update createState with failure state`() = runBlocking {
        val errorMessage = "Error occurred"
        val insertUseCase: InsertUseCase = mockk()
        coEvery { insertUseCase.invoke(any()) } returns flow {
            emit(ResultState.Error(errorMessage))
        }

        val viewModel = CreateViewModel(insertUseCase)
        viewModel.insertItem("Test Todo")

        val failureState: CreateState = viewModel.createState.value
        assertEquals(false, failureState.isLoading)
        assertEquals(errorMessage, failureState.error)
        assertEquals(false, failureState.status)
    }

    @Test
    fun `insertItem should handle error text and update createState with loading and error message`() =
        runBlocking {
            val dispatcher = TestCoroutineDispatcher()
            Dispatchers.setMain(dispatcher)
            val insertUseCase: InsertUseCase = mockk()
            val viewModel = CreateViewModel(insertUseCase)
            viewModel.insertItem("Error")

            // Assert loading state
            val loadingState: CreateState = viewModel.createState.value
            assertEquals(true, loadingState.isLoading)
            assertEquals("", loadingState.error)
            assertEquals(false, loadingState.status)

            // Advance time by 3000ms to simulate delay
            dispatcher.scheduler.apply { advanceTimeBy(3000); runCurrent() }

            // Assert error state after delay
            val errorState: CreateState = viewModel.createState.value
            assertEquals(false, errorState.isLoading)
            assertEquals("Failed to add TODO", errorState.error)
            assertEquals(false, errorState.status)

            dispatcher.cleanupTestCoroutines()
        }

}