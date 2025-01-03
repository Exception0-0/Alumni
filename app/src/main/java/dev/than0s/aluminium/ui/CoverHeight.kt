package dev.than0s.aluminium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CoverHeight(
    val default: Dp = 128.dp
)

val LocalCoverSize = compositionLocalOf { CoverHeight() }

val MaterialTheme.coverHeight: CoverHeight
    @Composable
    @ReadOnlyComposable
    get() = LocalCoverSize.current