package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerIcons() {
    ShimmerBackground(
        modifier = Modifier
            .clip(CircleShape)
            .size(16.dp)
    )
}