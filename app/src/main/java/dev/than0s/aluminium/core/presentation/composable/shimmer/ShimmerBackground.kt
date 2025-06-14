package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.getCurrentColorTheme
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun ShimmerBackground(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(roundedCorners.default),
) {
    when (getCurrentColorTheme()) {
        ColorTheme.System -> isSystemInDarkTheme()
        ColorTheme.Dark -> true
        ColorTheme.Light -> false
    }.let {
        Surface(
            shape = shape,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = modifier,
            content = {}
        )
    }
}