package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.getCurrentColorTheme
import dev.than0s.aluminium.ui.roundedCorners

val ShimmerDarkColor = Color(0xFF575757)
val ShimmerLightColor = Color(0xFFCDCDCD)

@Composable
fun ShimmerBackground(
    modifier: Modifier = Modifier,
) {
    when (getCurrentColorTheme()) {
        ColorTheme.System -> isSystemInDarkTheme()
        ColorTheme.Dark -> true
        ColorTheme.Light -> false
    }.let {
        Surface(
            shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
            color = if (it) ShimmerDarkColor else ShimmerLightColor,
            modifier = modifier,
            content = {}
        )
    }
}