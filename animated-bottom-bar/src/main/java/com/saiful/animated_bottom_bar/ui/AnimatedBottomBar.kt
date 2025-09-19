package com.saiful.animated_bottom_bar.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem
import com.saiful.animated_bottom_bar.ui.util.extractTypeSafeRouteName

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun <T> AnimatedBottomBar(
    navController: NavHostController,
    bottomNavItem: List<BottomNavItem<T>>,
    bottomBarProperties: BottomBarProperties = BottomBarProperties(),
    onSelectItem: (BottomNavItem<T>, index: Int) -> Unit,
) {

    val itemsWidth by remember { mutableStateOf(FloatArray(bottomNavItem.size)) }
    val itemsOffsets = remember { mutableStateListOf<Offset>() }

    var indicatorOffsetX by remember { mutableStateOf(0.dp) }
    var indicatorBoxWidth by remember { mutableStateOf(0.dp) }

    val currentIndex: MutableIntState = remember { mutableIntStateOf(0) }


    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val newIndex = findRouteIndex(destination.route, bottomNavItem)
            if (newIndex >= 0) currentIndex.intValue = newIndex
        }
    }


    LaunchedEffect(bottomNavItem.size) {
        if (itemsOffsets.size < bottomNavItem.size) {
            itemsOffsets.addAll(List(bottomNavItem.size - itemsOffsets.size) { Offset.Zero })
        }
    }

    val indicatorOffsetAnim by animateFloatAsState(
        targetValue = itemsOffsets.getOrNull(currentIndex.intValue)?.x ?: 0f,
        label = ""
    )


    val density = LocalDensity.current

    LaunchedEffect(key1 = itemsWidth[currentIndex.intValue]) {
        indicatorBoxWidth = with(density) { itemsWidth[currentIndex.intValue].toDp() }
    }

    LaunchedEffect(key1 = indicatorOffsetAnim) {
        indicatorOffsetX = with(density) { indicatorOffsetAnim.toDp() }
    }


    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bottomBarProperties.background)
                .height(70.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {

                Box(
                    modifier = Modifier
                        .width(indicatorBoxWidth)
                        .height(50.dp)
                        .offset(x = indicatorOffsetX)
                        .clip(bottomBarProperties.indicatorShape)
                        .background(bottomBarProperties.indicatorColor)
                )

                Row(
                    modifier = Modifier
                        .then(Modifier.fillMaxWidth())
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = bottomBarProperties.itemArrangement,
                ) {
                    bottomNavItem.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    onSelectItem(item, index)
                                }
                                .then(
                                    Modifier
                                        .onSizeChanged {
                                            itemsWidth[index] = it.width.toFloat()
                                        }
                                        .onGloballyPositioned { layoutCoordinates ->
                                            val position = layoutCoordinates.positionInParent()
                                            if (index < itemsOffsets.size) {
                                                itemsOffsets[index] = position
                                            } else {
                                                itemsOffsets.add(position)
                                            }
                                        })
                                .padding(horizontal = bottomBarProperties.itemPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            val tintColor =
                                if (currentIndex.intValue == index) bottomBarProperties.selectedIconColor else bottomBarProperties.unselectedIconColor

                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.name,
                                modifier = Modifier.size(bottomBarProperties.iconSize),
                                tint = tintColor
                            )

                            AnimatedVisibility(visible = index == currentIndex.intValue) {
                                Text(
                                    text = item.name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = bottomBarProperties.labelTextStyle,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun findRouteIndex(route: String?, bottomNavItem: List<BottomNavItem<*>>): Int {
    if (route == null) return -1

    return bottomNavItem.indexOfFirst { item ->
        when (val itemRoute = item.route) {
            is String -> itemRoute == route
            else -> {
                val routeString = itemRoute.toString()
                routeString == route.extractTypeSafeRouteName()
            }
        }
    }
}