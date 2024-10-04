package dev.than0s.aluminium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.than0s.mydiary.ui.Elevation
import dev.than0s.mydiary.ui.LocalElevation


data class RoundCorners(
    val default: Dp = 16.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp,
)

val LocalRoundCorners = compositionLocalOf { RoundCorners() }

val MaterialTheme.roundCorners: RoundCorners
    @Composable
    @ReadOnlyComposable
    get() = LocalRoundCorners.current