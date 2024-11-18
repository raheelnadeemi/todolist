package com.example.todolist.screen.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolist.screen.CreateScreen
import com.example.todolist.screen.ListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.List.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.List.route) {
            ListScreen(navController)
        }
        composable(NavigationItem.Create.route) {
            CreateScreen{
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("status", it)
                navController.popBackStack()
            }
        }
    }
}