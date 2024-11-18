package com.example.todolist.screen.route

enum class Screen {
    LIST,
    CREATE,
}
sealed class NavigationItem(val route: String) {
    data object List : NavigationItem(Screen.LIST.name)
    data object Create : NavigationItem(Screen.CREATE.name)
}