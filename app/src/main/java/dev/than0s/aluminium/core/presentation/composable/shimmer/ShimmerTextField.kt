package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerTextField(
    modifier: Modifier = Modifier
) {
    ShimmerBackground(
        modifier = modifier
            .height(54.dp)
    )
}