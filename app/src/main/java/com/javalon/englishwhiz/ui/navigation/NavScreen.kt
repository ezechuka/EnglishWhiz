package com.javalon.englishwhiz.ui.navigation

sealed class NavScreen(val route: String) {
    object HomeScreen: NavScreen(route = "home")
    object RandomWordScreen: NavScreen(route = "randomWord")
    object HistoryScreen: NavScreen(route = "history")
}