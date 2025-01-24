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
import com.saiful.custombottomnavbar.ui.BottomNavBar
import com.saiful.custombottomnavbar.ui.BottomNavItem
import com.saiful.custombottomnavbar.ui.theme.CustomBottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomBottomNavBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            initialIndex = remember { mutableIntStateOf(0) },
                            bottomNavItem = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = R.drawable.ic_launcher_foreground
                                ),
                                BottomNavItem(
                                    name = "Searching Value",
                                    route = "search",
                                    icon = R.drawable.ic_launcher_foreground
                                ),
                                BottomNavItem(
                                    name = "Ty",
                                    route = "search",
                                    icon = R.drawable.ic_launcher_foreground
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    BottomNavBar(
        bottomNavItem = listOf(
            BottomNavItem(
                name = "Home",
                route = "home",
                icon = R.drawable.ic_launcher_foreground
            ),
            BottomNavItem(
                name = "Search",
                route = "search",
                icon = R.drawable.ic_launcher_foreground
            ),
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomBottomNavBarTheme {
        Greeting("Android")
    }
}