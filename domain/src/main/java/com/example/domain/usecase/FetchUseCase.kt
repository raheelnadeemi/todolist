package com.example.domain.usecase

import android.content.Context
import com.example.domain.R
import com.example.domain.helper.ResultState
import com.example.domain.model.EntityModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchUseCase @Inject constructor(
    private val repository: Repository,
    private val context: Context
) {

    operator fun invoke(): Flow<ResultState<List<EntityModel>>> = flow {
        emit(ResultState.Loading)
        delay(3000)
        val items = repository.fetchItems()
        emit(ResultState.Success(items))
    }.catch {
        val errorMessage = context.resources.getString(
            R.string.Something_went_wrong)
        emit(ResultState.Error(errorMessage))
    }

    suspend fun fetchUpdated(): List<EntityModel> =
        repository.fetchItems()

}