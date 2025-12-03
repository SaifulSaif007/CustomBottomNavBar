package com.saiful.custombottomnavbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saiful.animated_bottom_bar.ui.AnimatedBottomBar
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem
import com.saiful.custombottomnavbar.ui.Routes
import com.saiful.custombottomnavbar.ui.navgraph.searchNavGraph
import com.saiful.custombottomnavbar.ui.screen.Screen
import com.saiful.custombottomnavbar.ui.theme.CustomBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomBottomNavBarTheme {

                val navController = rememberNavController()
                val showBottomNavbar = remember { mutableStateOf(false) }

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    Log.d("TAG", "current destination: ${destination.route}")
                    if (destination.hasRoute(Routes.Details::class)) {
                        showBottomNavbar.value = false
                    } else {
                        showBottomNavbar.value = true
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showBottomNavbar.value,
                            enter = slideInVertically(
                                initialOffsetY = { it } // starts from bottom
                            ) + fadeIn(),
                            exit = slideOutVertically(
                                targetOffsetY = { it } // goes to bottom
                            ) + fadeOut()
                        ) {
                            AnimatedBottomBar(
                                navController = navController,
                                bottomNavItem = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = Routes.Home,
                                        icon = R.drawable.ic_home
                                    ),
                                    BottomNavItem(
                                        name = "Settings",
                                        route = Routes.Settings,
                                        icon = R.drawable.ic_setting
                                    ),
                                    BottomNavItem(
                                        name = "Search",
                                        route = Routes.Search,
                                        icon = R.drawable.ic_search
                                    ),
                                    BottomNavItem(
                                        name = "Profile",
                                        route = Routes.Profile,
                                        icon = R.drawable.ic_profile
                                    ),
                                ),
                                bottomBarProperties = BottomBarProperties(
                                    itemArrangement = Arrangement.SpaceBetween
                                ),
                                onSelectItem = { item, _ ->
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { paddingValues ->

                    Box(modifier = Modifier){
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Home,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable<Routes.Home> {
                                Screen("Home")
                            }

                            searchNavGraph(navController = navController)

                            composable<Routes.Profile> {
                                Screen("Profile")
                            }
                            composable<Routes.Settings> {
                                Screen("Setting")
                            }

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