package com.saiful.custombottomnavbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saiful.animated_bottom_bar.ui.AnimatedBottomBar
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem
import com.saiful.custombottomnavbar.ui.screen.Screen
import com.saiful.custombottomnavbar.ui.theme.CustomBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomBottomNavBarTheme {

                val navController = rememberNavController()

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    Log.d("TAG", "current destination: ${destination.route}")
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedBottomBar(
                            bottomNavItem = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = R.drawable.ic_home
                                ),
                                BottomNavItem(
                                    name = "Settings",
                                    route = "setting",
                                    icon = R.drawable.ic_setting
                                ),
                                BottomNavItem(
                                    name = "Search",
                                    route = "search",
                                    icon = R.drawable.ic_search
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route = "profile",
                                    icon = R.drawable.ic_profile
                                ),
                            ),
                            bottomBarProperties = BottomBarProperties(
                                itemArrangement = Arrangement.SpaceBetween
                            ),
                            onSelectItem = { item, _ ->
                                navController.navigate(item.route) {
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
    CustomBottomNavBarTheme {}
}