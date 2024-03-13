package com.munyroth.majorrecommendation.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.ui.fragment.Home
import com.munyroth.majorrecommendation.ui.fragment.More
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.MainViewModel

sealed class Screen(val route: String, val label: Int, val iconResId: Int) {
    data object Home : Screen("home", R.string.title_home, R.drawable.ic_home)
    data object More : Screen("more", R.string.title_more, R.drawable.ic_menu_dots)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()

    // Initialize title as a mutable state
    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = title,
                    )
                },
                navigationIcon = {
                    Image(
                        modifier = Modifier.width(64.dp),
                        painter = painterResource(id = R.drawable.ic_app),
                        contentDescription = null,
                    )
                }
            )
        },
        bottomBar = {
            val items = listOf(
                Screen.Home,
                Screen.More
            )

            NavigationBar {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                title = when (currentRoute) {
                    Screen.Home.route -> stringResource(id = Screen.Home.label)
                    Screen.More.route -> stringResource(id = Screen.More.label)
                    // Add more cases for additional screens if necessary
                    else -> title // Fallback to the current title
                }

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconResId),
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(id = screen.label)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { Home() }
            composable(Screen.More.route) { More(mainViewModel) }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewScreenMain() {
    AppTheme {
        MainScreen()
    }
}
