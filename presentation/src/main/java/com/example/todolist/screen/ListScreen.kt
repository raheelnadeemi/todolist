package com.example.todolist.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todolist.R
import com.example.todolist.screen.common.AppBar
import com.example.todolist.screen.route.NavigationItem
import com.example.todolist.view.TodoActivity
import com.example.todolist.viewmodel.ListViewModel
import kotlin.system.exitProcess


@Composable
fun ListScreen(
    navController: NavHostController,
    listViewModel: ListViewModel = hiltViewModel()
){

    val openDialog = remember { mutableStateOf(false)  }
    val response = listViewModel.listState



    LaunchedEffect(Unit) {
        val status =
            navController.currentBackStackEntry?.savedStateHandle?.get<String>("status") ?: ""
        when(status){
            "Inserted" -> {
                listViewModel.fetchUpdatedList()
                response.value = listViewModel.itemState
            }
            "Error" -> openDialog.value = true
        }
    }

    BackHandler {
        val activity = TodoActivity()
        activity.finish()
        exitProcess(0)
    }

    Scaffold(topBar = {
        AppBar(
            text = stringResource(id = R.string.app_name)
        )
    }, floatingActionButton = {
        FloatingButton {
            navController.navigate(NavigationItem.Create.route)
        }
    }) { paddingValues ->
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (response.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if(!response.value.isLoading){
                if (response.value.items.isNullOrEmpty()) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.empty_list_content)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        items(response.value.items!!.size, key =  { it }){
                            ItemRow(item = response.value.items!![it].item)
                        }
                    }
                }
            }

            if(openDialog.value) {
                AlertDialog(
                    containerColor = Color.White,
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    text = {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = stringResource(id = com.example.domain.R.string.failed_to_add_todo)
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }

}




@Composable
fun FloatingButton(onNavigate: () -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape,
        onClick = onNavigate
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = ""
        )
    }
}




@Composable
fun ItemRow(item: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation =  CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFececec),
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .background(color = Color(0xFFececec))
        ) {
            Text(
                text = item,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


