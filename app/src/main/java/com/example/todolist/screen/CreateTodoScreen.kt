package com.example.todolist.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.R
import com.example.todolist.helper.Status
import com.example.todolist.screen.common.AppBar
import com.example.todolist.viewmodel.CreateViewModel


@Composable
fun CreateTodoScreen(
    createViewModel: CreateViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
){

    val response by createViewModel.createState

    LaunchedEffect(response) {
        when {
            response.status -> onNavigate(Status.INSERTED.name)
            response.error.isNotEmpty() -> onNavigate(Status.ERROR.name)
        }
    }

    BackHandler {
        onNavigate(Status.BACK.name)
    }

    Scaffold(topBar = {
        AppBar(
            text = stringResource(id = R.string.app_name)
        )
    }) { paddingValues ->

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            var text by rememberSaveable { mutableStateOf("") }

            Column {

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 20.dp, 10.dp, 10.dp),
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    label = { Text(stringResource(id = R.string.enter_new_item)) }
                )

                Button(
                    onClick = {
                        if(text.isNotEmpty()) {
                            createViewModel.insertItem(text)
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(10.dp, 20.dp, 10.dp, 10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        stringResource(id = R.string.add_todo),
                        color = Color.White
                    )
                }
            }

            if (response.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

