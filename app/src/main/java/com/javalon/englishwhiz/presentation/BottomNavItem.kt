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
        "Home",
        R.drawable.home,
        NavScreen.HomeScreen.route
    ),
    BottomNavItem(
        "Daily tip",
        R.drawable.calendar,
        NavScreen.RandomWordScreen.route
    ),
    BottomNavItem(
        "History",
        R.drawable.history,
        NavScreen.HistoryScreen.route
    )
)