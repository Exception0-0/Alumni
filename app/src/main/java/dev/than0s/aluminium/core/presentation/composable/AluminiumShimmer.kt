package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.than0s.aluminium.ui.roundCorners

private val ShimmerColor = Color(0xFFE0E0E0)

@Composable
fun ShimmerBackground(
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        color = ShimmerColor,
        modifier = modifier,
        content = {}
    )
}

@Composable
fun ShimmerCircularBackground(
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = CircleShape,
        color = ShimmerColor,
        modifier = modifier,
        content = {}
    )
}