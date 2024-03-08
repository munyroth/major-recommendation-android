package com.munyroth.majorrecommendation.ui.screens

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.ui.activity.Screen
import com.munyroth.majorrecommendation.ui.components.BetterModalBottomSheet
import com.munyroth.majorrecommendation.ui.components.LanguageSettings
import com.munyroth.majorrecommendation.ui.fragment.Home
import com.munyroth.majorrecommendation.ui.fragment.More
import com.munyroth.majorrecommendation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Function to update showBottomSheet state
    val setShowBottomSheet: (Boolean) -> Unit = { showBottomSheet = it }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.app_name),
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

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconResId),
                                contentDescription = null // Provide a proper content description
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
            composable(Screen.More.route) { More(showBottomSheet, setShowBottomSheet) }
        }

        BetterModalBottomSheet(
            showSheet = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            LanguageSettings(
                setShowBottomSheet = setShowBottomSheet
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreenMain() {
    AppTheme {
        MainScreen()
    }
}