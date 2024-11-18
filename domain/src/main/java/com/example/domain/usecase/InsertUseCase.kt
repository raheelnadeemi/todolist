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

class InsertUseCase @Inject constructor(
    private val repository: Repository,
    private val context: Context
) {

    operator fun invoke(model: EntityModel): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading)
        delay(3000)
        if(model.item.lowercase().trim() == context.resources.getString(
                R.string.error)){
            emit(ResultState.Error(context.resources.getString(
                R.string.failed_to_add_todo)))
        }else{
            repository.insertItem(model)
            emit(ResultState.Success(Unit))
        }
    }.catch {
        val errorMessage = context.resources.getString(
            R.string.Something_went_wrong)
        emit(ResultState.Error(errorMessage))
    }


}