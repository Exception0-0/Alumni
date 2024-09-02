package dev.than0s.mydiary.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextSize(
    val tiny: TextUnit = 10.sp,
    val extraSmall: TextUnit = 12.sp,
    val small: TextUnit = 14.sp,
    val medium: TextUnit = 16.sp,
    val large: TextUnit = 18.sp,
    val extraLarge: TextUnit = 20.sp,
    val huge: TextUnit = 24.sp,
    val gigantic: TextUnit = 32.sp,
)

val LocalTextSize = compositionLocalOf { TextSize() }

val MaterialTheme.textSize: TextSize
    @Composable
    @ReadOnlyComposable
    get() = LocalTextSize.current