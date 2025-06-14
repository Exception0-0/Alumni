package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.than0s.aluminium.ui.padding

@Composable
fun PreferredRow(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(MaterialTheme.padding.medium),
    content: @Composable (RowScope.() -> Unit)
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier,
        content = content
    )
}