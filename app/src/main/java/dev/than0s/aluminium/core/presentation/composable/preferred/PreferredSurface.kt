package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredSurface(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
    content: @Composable () -> Unit,
) {
    Surface(
        shape = shape,
        color = color,
        modifier = modifier,
        content = content
    )
}