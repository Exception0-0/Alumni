package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.getCurrentColorTheme
import dev.than0s.aluminium.ui.roundedCorners

private val ShimmerDarkColor = Color(0xFF575757)
private val ShimmerLightColor = Color(0xFFCDCDCD)

@Composable
fun ShimmerBackground(
    shape: Shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
    modifier: Modifier = Modifier,
) {
    when (getCurrentColorTheme()) {
        ColorTheme.System -> isSystemInDarkTheme()
        ColorTheme.Dark -> true
        ColorTheme.Light -> false
    }.let {
        Surface(
            shape = shape,
            color = getCurrentShimmerBackground(),
            modifier = modifier,
            content = {}
        )
    }
}

@Composable
fun getCurrentShimmerBackground(): Color {
    return when (getCurrentColorTheme()) {
        ColorTheme.System -> isSystemInDarkTheme()
        ColorTheme.Dark -> true
        ColorTheme.Light -> false
    }.let {
        if (it) ShimmerDarkColor else ShimmerLightColor
    }
}