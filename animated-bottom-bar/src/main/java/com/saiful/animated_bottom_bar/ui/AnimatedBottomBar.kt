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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.animated_bottom_bar.R
import com.saiful.animated_bottom_bar.ui.model.BottomBarProperties
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem

@Composable
fun AnimatedBottomBar(
    bottomNavItem: List<BottomNavItem>,
    currentIndex: MutableIntState = remember { mutableIntStateOf(0) },
    bottomBarProperties: BottomBarProperties = BottomBarProperties(),
    onSelectedItem: (BottomNavItem) -> Unit = {},
) {

    var itemWidth by remember { mutableFloatStateOf(0f) }
    val itemsWidth by remember { mutableStateOf(FloatArray(bottomNavItem.size)) }

    val offsetAnim by animateFloatAsState(
        targetValue = when (currentIndex.value) {
            0 -> 0f
            else -> itemsWidth.min() * currentIndex.value
        },
        label = ""
    )

    var offsetAnimInDp by remember { mutableStateOf(0.dp) }
    var itemInDp by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    LaunchedEffect(key1 = itemWidth, block = {
        itemInDp = with(density) {
            itemsWidth[currentIndex.value].toDp()
        }
    })

    LaunchedEffect(key1 = offsetAnim, block = {
        println(offsetAnimInDp)
        offsetAnimInDp = with(density) { offsetAnim.toDp() }
    })


    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bottomBarProperties.background)
                .height(70.dp),
            horizontalArrangement = bottomBarProperties.itemArrangement,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
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
                        .wrapContentWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    bottomNavItem.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    onSelectedItem(item)
                                }
                                .onSizeChanged {
                                    itemWidth = it.width.toFloat()
                                    itemsWidth[index] = itemWidth
                                }
                                .padding(bottomBarProperties.itemPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            val tintColor =
                                if (currentIndex.value == index) bottomBarProperties.selectedIconColor else bottomBarProperties.unselectedIconColor

                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = "Home",
                                modifier = Modifier.size(bottomBarProperties.iconSize.dp),
                                tint = tintColor
                            )

                            AnimatedVisibility(visible = index == currentIndex.value) {
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

@Preview
@Composable
private fun BottomNavBarPreview() {
    AnimatedBottomBar(
        currentIndex = remember { mutableIntStateOf(0) },
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