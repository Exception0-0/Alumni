package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.than0s.aluminium.core.presentation.composable.preferred.ShimmerBackground

@Composable
fun ShimmerTextField(){
    ShimmerBackground(
        modifier = Modifier
            .height(54.dp)
    )
}