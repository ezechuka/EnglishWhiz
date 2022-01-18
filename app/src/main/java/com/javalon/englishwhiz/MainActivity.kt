package com.javalon.englishwhiz

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.javalon.englishwhiz.presentation.BottomNavItem
import com.javalon.englishwhiz.presentation.WordModelViewModel
import com.javalon.englishwhiz.presentation.provideBottomNavItems
import com.javalon.englishwhiz.ui.HomeScreen
import com.javalon.englishwhiz.ui.navigation.NavScreen
import com.javalon.englishwhiz.ui.theme.EnglishWhizTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var textToSpeechEngine: TextToSpeech

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishWhizTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val viewModel = hiltViewModel<WordModelViewModel>()
                    LaunchedEffect(key1 = true) {
                        viewModel.isRetrieved.collectLatest { result ->
//                            if (result)
//                                viewModel.search("log")
                        }
                    }

                    LaunchedEffect(key1 = true) {
                        viewModel.searchEvent.collectLatest {
                            Log.d(
                                "MainActivity",
                                viewModel.state.value.wordModel?.meanings.toString()
                            )
                        }
                    }

                    var onInit = false
                    textToSpeechEngine = TextToSpeech(this, { status ->
                        if (status == TextToSpeech.SUCCESS)
                            onInit = true
                    }, "com.google.android.tts")

                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = provideBottomNavItems(),
                                navController = navController
                            ) {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        }, scaffoldState = scaffoldState
                    ) {
                        Box(modifier = Modifier.padding(
                            top = 0.dp, start = 0.dp, end = 0.dp, bottom = it.calculateBottomPadding() + 4.dp
                        )) {
                            EnglishWhizNavigation(
                                navController = navController,
                                viewModel = viewModel,
                                scaffoldState, textToSpeechEngine, onInit
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        textToSpeechEngine.stop()
        super.onStop()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
}

@Composable
@ExperimentalAnimationApi
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    itemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    Card(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry?.destination?.route
                CustomNavigationItem(
                    item = item,
                    onClick = { itemClick(item) },
                    selected = selected
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun CustomNavigationItem(item: BottomNavItem, onClick: () -> Unit, selected: Boolean) {

    val backgroundColor =
        if (selected) MaterialTheme.colors.onSurface.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (selected) MaterialTheme.colors.onSurface else Color.Gray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painterResource(id = item.icon),
                tint = contentColor,
                contentDescription = item.title
            )
            Spacer(modifier = Modifier.padding(horizontal = 1.dp))
            AnimatedVisibility(visible = selected) {
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    color = contentColor,
                    fontSize = 13.sp,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
fun EnglishWhizNavigation(
    navController: NavHostController,
    viewModel: WordModelViewModel,
    scaffoldState: ScaffoldState,
    textToSpeechEngine: TextToSpeech,
    onInit: Boolean
) {
    NavHost(navController = navController, startDestination = NavScreen.HomeScreen.route) {
        composable(route = NavScreen.HomeScreen.route) {
            HomeScreen(viewModel, scaffoldState, textToSpeechEngine, onInit)
        }

        composable(route = NavScreen.RandomWordScreen.route) {

        }

        composable(route = NavScreen.HistoryScreen.route) {

        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    EnglishWhizTheme {
        BottomNavigationBar(
            items = provideBottomNavItems(),
            navController = rememberNavController()
        ) {}
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun BottomItemPreview() {
    EnglishWhizTheme {
        CustomNavigationItem(
            item = BottomNavItem("Home", R.drawable.home, "home"),
            onClick = { },
            selected = true
        )
    }
}