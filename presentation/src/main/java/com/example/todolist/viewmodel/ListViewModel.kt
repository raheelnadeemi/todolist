package com.example.todolist.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.helper.ResultState
import com.example.domain.usecase.FetchUseCase
import com.example.todolist.model.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val fetchUseCase: FetchUseCase
): ViewModel() {

    private val _listState = mutableStateOf(ListState())
    val listState: MutableState<ListState>
        get() = _listState

    var itemState = ListState(
        isLoading = false,
        items = listState.value.items,
        error = ""
    )

    init {
        fetchUseCase.invoke().onEach {
            _listState.value = when(it){
                is ResultState.Loading -> ListState(isLoading = true)
                is ResultState.Success -> {
                    ListState(items = it.data)
                }
                is ResultState.Error ->  ListState(error = it.error)
            }
        }.launchIn(viewModelScope)
    }

    suspend fun fetchUpdatedList() {
        itemState = ListState(items = fetchUseCase.fetchUpdated())
    }


}

