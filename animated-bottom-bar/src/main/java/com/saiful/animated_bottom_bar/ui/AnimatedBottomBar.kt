package com.saiful.animated_bottom_bar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.saiful.animated_bottom_bar.R
import com.saiful.animated_bottom_bar.ui.model.BottomNavItem

@Composable
fun AnimatedBottomBar(
    bottomNavItem: List<BottomNavItem>,
    initialIndex: MutableIntState = remember { mutableIntStateOf(0) },
) {

    var itemWidth by remember { mutableFloatStateOf(0f) }
    val itemsWidth by remember { mutableStateOf(FloatArray(bottomNavItem.size)) }

    val offsetAnim by animateFloatAsState(
        targetValue = when (initialIndex.value) {
            0 -> 0f
            else -> itemsWidth[0] * initialIndex.value
        },
        label = ""
    )

    var offsetAnimInDp by remember { mutableStateOf(0.dp) }
    var itemInDp by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    LaunchedEffect(key1 = itemWidth, block = {
        itemInDp = with(density) {
            itemsWidth[initialIndex.value].toDp()
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
                .background(Color.White)
                .height(70.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(Color.Yellow.copy(alpha = .2f)),
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
                                    initialIndex.value = index
                                }
                                .onSizeChanged {
                                    itemWidth = it.width.toFloat()
                                    itemsWidth[index] = itemWidth
                                }
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            val tintColor =
                                if (initialIndex.value == index) Color.Cyan else Color.DarkGray

                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = "Home",
                                modifier = Modifier.size(24.dp),
                                tint = tintColor
                            )

                            AnimatedVisibility(visible = index == initialIndex.value) {
                                Text(
                                    text = item.name,
                                    color = tintColor,
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 5.dp),
                                )
                            }

                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .width(itemInDp)
                        .height(50.dp)
                        .offset(offsetAnimInDp)
                        .clip(RoundedCornerShape(24f))
                        .background(Color.Blue.copy(alpha = 0.2f))
                )
            }

        }
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    AnimatedBottomBar(
        initialIndex = remember { mutableIntStateOf(0) },
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
        )
    )
}