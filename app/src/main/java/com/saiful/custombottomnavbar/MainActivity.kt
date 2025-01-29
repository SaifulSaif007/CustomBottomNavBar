package com.saiful.custombottomnavbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*

import com.saiful.animated_bottom_bar.ui.AnimatedBottomBar
import com.saiful.custombottomnavbar.ui.screen.Screen

import com.saiful.custombottomnavbar.ui.theme.CustomBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomBottomNavBarTheme {

                val navController = rememberNavController()


                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val currentIndex = remember { mutableIntStateOf(0) }
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        currentIndex.value = when (currentDestination?.route) {
                            "home" -> 0
                            "search" -> 1
                            "profile" -> 2
                            "setting" -> 3
                            else -> 0
                        }

                        AnimatedBottomBar(
                            currentIndex = currentIndex,
                            bottomNavItem = listOf(
                                com.saiful.animated_bottom_bar.ui.model.BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = R.drawable.ic_home
                                ),
                                com.saiful.animated_bottom_bar.ui.model.BottomNavItem(
                                    name = "Search",
                                    route = "search",
                                    icon = R.drawable.ic_search
                                ),
                                com.saiful.animated_bottom_bar.ui.model.BottomNavItem(
                                    name = "Profile",
                                    route = "profile",
                                    icon = R.drawable.ic_profile
                                ),
                                com.saiful.animated_bottom_bar.ui.model.BottomNavItem(
                                    name = "Settings",
                                    route = "setting",
                                    icon = R.drawable.ic_setting
                                ),
                            ),
                            onSelectedItem = {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(route = "home") {
                            Screen("Home")
                        }
                        composable("search") {
                            Screen("Search")
                        }
                        composable("profile") {
                            Screen("Profile")
                        }
                        composable("setting") {
                            Screen("Setting")
                        }
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomBottomNavBarTheme {

    }
}