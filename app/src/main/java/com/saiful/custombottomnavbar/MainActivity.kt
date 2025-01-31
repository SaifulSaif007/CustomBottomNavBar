package com.saiful.custombottomnavbar

import android.os.Bundle
import android.util.Log
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
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.custombottomnavbar.ui.screen.Screen

import com.saiful.custombottomnavbar.ui.theme.CustomBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomBottomNavBarTheme {

                val navController = rememberNavController()

                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    Log.d("TAG", "current destination: ${destination.route}")
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedBottomBar(
                            navController = navController,
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
                            bottomBarProperties = BottomBarProperties(
                                itemArrangement = Arrangement.Start
                            ),
                            onSelectedItem = {
                                Log.d("TAG", "Selected Item: ${it.route}")
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
    CustomBottomNavBarTheme {}
}