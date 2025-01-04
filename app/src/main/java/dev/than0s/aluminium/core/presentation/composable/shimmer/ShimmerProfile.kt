package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import dev.than0s.aluminium.ui.profileSize

@Composable
fun ShimmerProfileImage(
    size: Dp = MaterialTheme.profileSize.medium
) {
    ShimmerBackground(
        modifier = Modifier
            .clip(CircleShape)
            .size(size)
    )
}