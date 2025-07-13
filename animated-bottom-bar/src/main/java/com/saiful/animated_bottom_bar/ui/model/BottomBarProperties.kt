package com.saiful.animated_bottom_bar.ui.model


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomBarProperties(
    val background: Color = Color.Blue.copy(alpha = 0.7f),
    val selectedIconColor: Color = Color.Unspecified,
    val unselectedIconColor: Color = Color.Unspecified,
    val labelTextStyle: TextStyle =
        TextStyle(
            color = Color.White,
            fontSize = 14.sp
        ),
    val iconSize: Int = 24,
    val itemPadding: PaddingValues = PaddingValues(all = 16.dp),
    val itemArrangement: Arrangement.Horizontal = Arrangement.Start,
    val indicatorColor: Color = Color.Blue.copy(alpha = 0.5f),
    val indicatorShape: Shape = RoundedCornerShape(12.dp),
    val spreadItems: Boolean = true // If true, items are spread evenly across the full width
)
