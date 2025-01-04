package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PreferredCircularProgressIndicator(
    size: Dp = PreferredCircularProgressIndicatorSize.small,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

data object PreferredCircularProgressIndicatorSize {
    val small: Dp = 24.dp
    val default: Dp = 32.dp
}