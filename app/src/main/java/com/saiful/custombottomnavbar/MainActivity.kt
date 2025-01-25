package com.saiful.custombottomnavbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.saiful.animated_bottom_bar.ui.AnimatedBottomBar

import com.saiful.custombottomnavbar.ui.theme.CustomBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomBottomNavBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedBottomBar(
                            initialIndex = remember { mutableIntStateOf(0) },
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
                            )
                        )
                    }
                ) { paddingValues ->

                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "Hello", modifier = Modifier.padding(paddingValues))
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