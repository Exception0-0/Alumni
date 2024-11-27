package dev.than0s.aluminium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Size(
    val default: Dp = 128.dp,
    val extraSmall: Dp = 32.dp,
    val small: Dp = 64.dp,
    val medium: Dp = 128.dp,
    val large: Dp = 256.dp,
    val extraLarge: Dp = 512.dp,
)

val LocalSize = compositionLocalOf { Size() }

val MaterialTheme.Size: Size
    @Composable
    @ReadOnlyComposable
    get() = LocalSize.current