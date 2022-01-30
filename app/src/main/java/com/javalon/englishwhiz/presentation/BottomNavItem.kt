package com.javalon.englishwhiz.presentation

import com.javalon.englishwhiz.R
import com.javalon.englishwhiz.ui.navigation.NavScreen

data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)

fun provideBottomNavItems() = listOf(
    BottomNavItem(
        NavScreen.HomeScreen.route,
        R.drawable.home,
        NavScreen.HomeScreen.routeWithArgument
    ),
    BottomNavItem(
        NavScreen.BookmarkScreen.route,
        R.drawable.save,
        NavScreen.BookmarkScreen.route
    ),
    BottomNavItem(
        NavScreen.HistoryScreen.route,
        R.drawable.history,
        NavScreen.HistoryScreen.route
    )
)