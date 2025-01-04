package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerText(
    height: Dp = ShimmerTextHeight.medium,
    width: Dp = ShimmerTextWidth.medium,
) {
    ShimmerBackground(
        modifier = Modifier
            .height(height)
            .width(width)
    )
}

data object ShimmerTextHeight {
    val small: Dp = 8.dp
    val medium: Dp = 16.dp
    val high: Dp = 32.dp
}

data object ShimmerTextWidth {
    val small: Dp = 64.dp
    val medium: Dp = 128.dp
    val high: Dp = 256.dp
}