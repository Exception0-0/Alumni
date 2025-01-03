package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun AluminiumFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        content = content,
        modifier = modifier
    )
}