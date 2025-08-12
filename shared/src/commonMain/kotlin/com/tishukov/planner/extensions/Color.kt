package com.tishukov.planner.extensions

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromHex(hex: String): Color {
    val cleanHexString = hex.replace("#", "")
    val hexColor = cleanHexString.toLongOrNull(16) ?: error("Invalid hex color: $hex")
    val alpha = if (cleanHexString.length == 8) (hexColor shr 24 and 0xFF) / 255f else 1f
    val red = (hexColor shr 16 and 0xFF) / 255f
    val green = (hexColor shr 8 and 0xFF) / 255f
    val blue = (hexColor and 0xFF) / 255f

    return Color(red = red, green = green, blue = blue, alpha = alpha)
}
