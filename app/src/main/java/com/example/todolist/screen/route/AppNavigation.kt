package com.example.todolist.screen.route

sealed class NavigationItem(val route: String) {
    data object List : NavigationItem(List::class.java.simpleName)
    data object Create : NavigationItem(Create::class.java.simpleName)
}