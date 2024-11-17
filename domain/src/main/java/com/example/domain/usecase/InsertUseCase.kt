package com.example.domain.usecase

import com.example.domain.helper.ResultState
import com.example.domain.repository.Repository
import com.example.domain.transformation.Transformer
import com.example.todolist.model.EntityModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUseCase @Inject constructor(
    private val repository: Repository,
    private val transformer: Transformer) {

    operator fun invoke(model: EntityModel): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading)
        delay(3000L)
        repository.insertItem(
            transformer.transformModelToBean(model)
        )
        emit(ResultState.Success(Unit))
    }.catch {
        val errorMessage = "Something went wrong"
        emit(ResultState.Error(errorMessage))
    }


}