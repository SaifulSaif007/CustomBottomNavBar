package com.saiful.animated_bottom_bar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saiful.animated_bottom_bar.R
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem
import kotlinx.coroutines.delay

@Composable
fun AnimatedBottomBar(
    navController: NavHostController,
    bottomNavItem: List<BottomNavItem>,
    bottomBarProperties: BottomBarProperties = BottomBarProperties(),
    onSelectItem: (BottomNavItem) -> Unit = {},
) {

    val currentIndex: MutableIntState = remember { mutableIntStateOf(0) }
    var itemWidth by remember { mutableFloatStateOf(0f) }
    val itemsWidth by remember { mutableStateOf(FloatArray(bottomNavItem.size)) }

    // New: Track total width for spread mode
    var totalWidth by remember { mutableFloatStateOf(0f) }

    val offsetAnim by animateFloatAsState(
        targetValue = if (bottomBarProperties.spreadItems) {
            // Spread mode: offset = index * (totalWidth/itemCount)
            // offset = (totalWidth / bottomNavItem.size) * currentIndex.intValue
            // plus  [(totalWidth / bottomNavItem.size) - itemWidth] / 2
            val singleItemWidth = totalWidth / bottomNavItem.size
            if (bottomNavItem.isNotEmpty()) {
                when (currentIndex.intValue) {
                    0 -> 0f
                    else -> singleItemWidth * currentIndex.intValue + (singleItemWidth - itemWidth) / 2
                }
            } else 0f
        } else {
            // Old logic
            when (currentIndex.intValue) {
                0 -> 0f
                else -> itemsWidth.min() * currentIndex.intValue
            }
        },
        label = ""
    )

    var offsetAnimInDp by remember { mutableStateOf(0.dp) }
    var itemInDp by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    LaunchedEffect(key1 = itemWidth) {
        itemInDp = with(density) { itemsWidth[currentIndex.intValue].toDp() }
    }

    LaunchedEffect(key1 = offsetAnim) {
        offsetAnimInDp = with(density) { offsetAnim.toDp() }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    currentIndex.intValue = getBottomNavIndex(currentDestination?.route, bottomNavItem)

    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bottomBarProperties.background)
                .height(70.dp)
                .onSizeChanged {
                    if (bottomBarProperties.spreadItems) totalWidth = it.width.toFloat()
                },
            horizontalArrangement = bottomBarProperties.itemArrangement,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {

                Box(
                    modifier = Modifier
                        .width(itemInDp)
                        .height(50.dp)
                        .offset(offsetAnimInDp)
                        .clip(bottomBarProperties.indicatorShape)
                        .background(bottomBarProperties.indicatorColor)
                )

                Row(
                    modifier = Modifier
                        .then(
                            if (bottomBarProperties.spreadItems) Modifier.fillMaxWidth() else Modifier.wrapContentWidth()
                        )
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (bottomBarProperties.spreadItems) Arrangement.SpaceBetween else Arrangement.Start
                ) {
                    bottomNavItem.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    onSelectItem(item)
                                }
                                .then(
                                    Modifier.onSizeChanged {
                                        println("size $it index $index")
                                        itemWidth = it.width.toFloat()
                                        itemsWidth[index] = itemWidth
                                    }
                                )
                                .padding(bottomBarProperties.itemPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            val tintColor =
                                if (currentIndex.intValue == index) bottomBarProperties.selectedIconColor else bottomBarProperties.unselectedIconColor

                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = "Home",
                                modifier = Modifier.size(bottomBarProperties.iconSize.dp),
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

@Composable
private fun getBottomNavIndex(
    currentDestinationRoute: String?,
    bottomNavItems: List<BottomNavItem>
): Int {
    return when {
        currentDestinationRoute == null -> 0
        else -> bottomNavItems.indexOfFirst { it.route == currentDestinationRoute }
            .takeIf { it != -1 } ?: 0
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    AnimatedBottomBar(
        navController = rememberNavController(),
        bottomNavItem = listOf(
            BottomNavItem(
                name = "Home",
                route = "home",
                icon = R.drawable.ic_home
            ),
            BottomNavItem(
                name = "Search Value sldl",
                route = "search",
                icon = R.drawable.ic_home
            ),
            BottomNavItem(
                name = "T",
                route = "search",
                icon = R.drawable.ic_home
            ),
        ),
    )
}