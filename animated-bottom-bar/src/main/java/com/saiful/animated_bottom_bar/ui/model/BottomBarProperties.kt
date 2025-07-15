package com.saiful.animated_bottom_bar.ui.model


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomBarProperties(
    val background: Color = Color.Blue.copy(alpha = 0.7f),
    val selectedIconColor: Color = Color.White,
    val unselectedIconColor: Color = Color.Black,
    val labelTextStyle: TextStyle =
        TextStyle(
            color = Color.White,
            fontSize = 14.sp
        ),
    val iconSize: Dp = 24.dp,
    val itemPadding: Dp = 12.dp,
    val itemArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
    val indicatorColor: Color = Color.Blue.copy(alpha = 0.5f),
    val indicatorShape: Shape = RoundedCornerShape(12.dp),
)
