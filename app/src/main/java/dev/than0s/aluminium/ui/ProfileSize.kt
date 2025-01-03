package dev.than0s.aluminium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ProfileSize(
    val small: Dp = 20.dp,
    val medium: Dp = 40.dp,
    val large: Dp = 80.dp,
)

val LocalProfileSize = compositionLocalOf { ProfileSize() }

val MaterialTheme.profileSize: ProfileSize
    @Composable
    @ReadOnlyComposable
    get() = LocalProfileSize.current