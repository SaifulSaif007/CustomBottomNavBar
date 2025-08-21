package com.saiful.animated_bottom_bar.ui.model

/**
 * @author Saiful Islam
 * @param name [String] route name
 * @param route [Any] will support string routes & type safe navigation object
 * @param icon [Int] a resource id of the icon
 * */
data class BottomNavItem<T>(
    val name: String,
    val route: T,
    val icon: Int,
)