package com.saiful.animated_bottom_bar.ui.model

import androidx.compose.runtime.Stable

/**
 * @author Saiful Islam
 * @param name [String] route name
 * @param route [T] generic route type, should be String for String-based navigation
 * or object or data class for Type-safe navigation
 * @param icon [Int] a resource id of the icon
 * */

@Stable
data class BottomNavItem<T>(
    val name: String,
    val route: T,
    val icon: Int,
)