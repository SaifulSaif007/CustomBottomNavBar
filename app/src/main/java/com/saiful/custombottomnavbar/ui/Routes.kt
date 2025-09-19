package com.saiful.custombottomnavbar.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object Home : Routes()

    @Serializable
    data object Search : Routes()

    @Serializable
    data object Profile : Routes()

    @Serializable
    data object Settings : Routes()

    @Serializable
    data class Details(val id: Int = 0) : Routes()

}