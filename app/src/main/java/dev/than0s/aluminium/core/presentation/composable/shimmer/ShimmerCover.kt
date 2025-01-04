package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.than0s.aluminium.ui.coverHeight

@Composable
fun ShimmerCover(
    height: Dp = MaterialTheme.coverHeight.default
) {
    ShimmerBackground(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}