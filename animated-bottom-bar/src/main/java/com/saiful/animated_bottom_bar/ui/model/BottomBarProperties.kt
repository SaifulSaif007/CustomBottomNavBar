package com.saiful.animated_bottom_bar.ui.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomBarProperties(
    val background: Color = Color.White,
    val selectedIconColor: Color = Color.Unspecified,
    val unselectedIconColor: Color = Color.Unspecified,
    val labelTextStyle: TextStyle =
        TextStyle(
            color = Color.Black,
            fontSize = 14.sp
        ),
    val iconSize: Int = 24,
    val itemPadding: PaddingValues = PaddingValues(all = 16.dp)
)
