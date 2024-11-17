package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.InsertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(private val insertUseCase: InsertUseCase):ViewModel() {
}