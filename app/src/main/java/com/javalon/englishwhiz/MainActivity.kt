package com.javalon.englishwhiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.javalon.englishwhiz.presentation.main.MainScreen
import com.javalon.englishwhiz.presentation.main.components.BottomNavigationBar
import com.javalon.englishwhiz.presentation.main.components.provideBottomNavItems
import com.javalon.englishwhiz.presentation.ui.theme.EnglishWhizTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@InternalCoroutinesApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentTheme = isSystemInDarkTheme()
            val toggleTheme = { if (currentTheme) setDayTheme() else setNightTheme() }

            EnglishWhizTheme {

                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = provideBottomNavItems(),
                                navController
                            ) {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        }, scaffoldState = scaffoldState
                    ) {
                        Box(
                            modifier = Modifier.padding(
                                bottom = it.calculateBottomPadding()
                            )
                        ) {
                            MainScreen(
                                navController = navController,
                                scaffoldState = scaffoldState,
                                toggleTheme = toggleTheme
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setDayTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setNightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}