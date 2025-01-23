package com.saiful.custombottomnavbar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.custombottomnavbar.R

@Composable
fun BottomNavBar(
    bottomNavItem: List<BottomNavItem>,
    initialIndex: MutableIntState,
) {

    var width by remember { mutableFloatStateOf(0f) }
    var itemWidth by remember { mutableFloatStateOf(0f) }

    val offsetAnim by animateFloatAsState(
        targetValue = when (initialIndex.value) {
            0 -> 0f
            else -> initialIndex.value * itemWidth
        },
        label = ""
    )

    var offsetAnimInDp by remember { mutableStateOf(0.dp) }
    var itemInDp by remember { mutableStateOf(30.dp) }

    val density = LocalDensity.current

    LaunchedEffect(key1 = itemWidth, block = {
        itemInDp = with(density) { itemWidth.toDp() }
    })

    LaunchedEffect(key1 = offsetAnim, block = {
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
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {

                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .onGloballyPositioned {
                            width = it.size.width.toFloat()
                            itemWidth = width / bottomNavItem.size
                        },
                    containerColor = Color.White,
                    tonalElevation = 0.dp,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    bottomNavItem.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .padding(5.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    initialIndex.value = index
                                },
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
                        .clip(RoundedCornerShape(50))
                        .background(Color.Blue.copy(alpha = 0.2f))
                )
            }

        }
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    BottomNavBar(
        initialIndex = remember { mutableIntStateOf(0) },
        bottomNavItem = listOf(
            BottomNavItem(
                name = "Home",
                route = "home",
                icon = R.drawable.ic_launcher_foreground
            ),
            BottomNavItem(
                name = "Search",
                route = "search",
                icon = R.drawable.ic_launcher_foreground
            ),
            BottomNavItem(
                name = "Search",
                route = "search",
                icon = R.drawable.ic_launcher_foreground
            ),
        )
    )
}