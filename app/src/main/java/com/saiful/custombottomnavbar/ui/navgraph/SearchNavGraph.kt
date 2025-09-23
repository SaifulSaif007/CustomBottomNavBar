package com.saiful.custombottomnavbar.ui.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saiful.custombottomnavbar.ui.Routes
import com.saiful.custombottomnavbar.ui.screen.SearchDetailsScreen
import com.saiful.custombottomnavbar.ui.screen.SearchScreen

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    navigation<Routes.Search>(startDestination = Routes.SearchHome) {
        composable<Routes.SearchHome> {
            SearchScreen(onSearch = {
                navController.navigate(Routes.Details(2))
            })
        }

        composable<Routes.Details> {
            SearchDetailsScreen()
        }
    }
}