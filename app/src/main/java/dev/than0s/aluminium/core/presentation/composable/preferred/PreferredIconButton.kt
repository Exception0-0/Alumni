package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PreferredIconButton(
    icon: ImageVector,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        enabled = !isLoading,
        modifier = modifier
    ) {
        if (isLoading) {
            PreferredCircularProgressIndicator()
        } else {
            Icon(
                imageVector = icon,
                contentDescription = icon.name
            )
        }
    }
}
