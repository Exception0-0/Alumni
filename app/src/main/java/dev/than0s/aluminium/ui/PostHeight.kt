package dev.than0s.aluminium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PostHeight(
    val default: Dp = 450.dp,
    val small: Dp = 100.dp
)

val LocalPostHeight = compositionLocalOf { PostHeight() }

val MaterialTheme.postHeight: PostHeight
    @Composable
    @ReadOnlyComposable
    get() = LocalPostHeight.current