package dev.than0s.aluminium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class RoundedCorners(
    val default: Dp = 8.dp,
    val verySmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 6.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 10.dp,
    val extraLarge: Dp = 12.dp,
    val veryLarge: Dp = 14.dp
)

val LocalRoundCorners = compositionLocalOf { RoundedCorners() }

val MaterialTheme.roundedCorners: RoundedCorners
    @Composable
    @ReadOnlyComposable
    get() = LocalRoundCorners.current