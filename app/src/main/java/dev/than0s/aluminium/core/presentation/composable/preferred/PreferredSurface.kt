package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredSurface(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        color = color,
        modifier = modifier,
        content = content
    )
}