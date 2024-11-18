package com.example.todolist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.helper.ResultState
import com.example.domain.model.EntityModel
import com.example.domain.usecase.InsertUseCase
import com.example.todolist.model.CreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val insertUseCase: InsertUseCase
):ViewModel() {

    private val _createState = mutableStateOf(CreateState())
    val createState: State<CreateState> = _createState

    fun insertItem(text: String) {
        viewModelScope.launch {
            insertUseCase.invoke(EntityModel(item = text)).collect { result ->
                _createState.value = when (result) {
                    is ResultState.Loading -> CreateState(isLoading = true)
                    is ResultState.Success -> CreateState(status = true)
                    is ResultState.Error -> CreateState(error = result.error)
                }
            }
        }
    }

}