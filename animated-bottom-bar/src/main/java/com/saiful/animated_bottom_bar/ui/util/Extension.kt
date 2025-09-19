package com.saiful.animated_bottom_bar.ui.util

internal fun String.extractTypeSafeRouteName(): String {
    val regex = """\.([A-Za-z0-9_]+)(?:[/?].*)?$""".toRegex()
    return regex.find(this)?.groupValues?.get(1) ?: ""
}