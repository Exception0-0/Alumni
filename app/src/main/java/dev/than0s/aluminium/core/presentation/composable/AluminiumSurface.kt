package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun AluminiumSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        modifier = modifier,
        content = content
    )
}