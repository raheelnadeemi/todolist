package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.FetchUseCase
import javax.inject.Inject

class ListViewModel @Inject constructor(private val fetchUseCase: FetchUseCase): ViewModel() {
}