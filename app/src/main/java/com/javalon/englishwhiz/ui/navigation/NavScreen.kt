package com.javalon.englishwhiz.ui.navigation

sealed class NavScreen(val route: String) {
    object HomeScreen: NavScreen(route = "Home") {
        const val routeWithArgument = "Home?wordIndex={wordIndex}?bookmark={bookmark}"
    }
    object BookmarkScreen: NavScreen(route = "Bookmark")
    object HistoryScreen: NavScreen(route = "History")
}