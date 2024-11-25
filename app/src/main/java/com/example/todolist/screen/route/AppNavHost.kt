package com.example.todolist.screen.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolist.R
import com.example.todolist.screen.CreateTodoScreen
import com.example.todolist.screen.TodoListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.List.route
) {
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.List.route) {
            TodoListScreen(navController)
        }
        composable(NavigationItem.Create.route) {
            CreateTodoScreen{
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(context.getString(R.string.status), it)
                navController.popBackStack()
            }
        }
    }
}