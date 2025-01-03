package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun AluminiumElevatedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        content = content,
        modifier = modifier
    )
}

@Composable
fun AluminiumElevatedCard(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    ElevatedCard(
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        content = content,
        modifier = modifier
    )
}