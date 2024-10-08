package dev.than0s.aluminium.core.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.than0s.aluminium.ui.roundCorners

@Composable
fun AluminiumCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        modifier = modifier,
        content = content,
    )
}

@Composable
fun AluminiumCard(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        modifier = modifier,
        content = content,
    )
}