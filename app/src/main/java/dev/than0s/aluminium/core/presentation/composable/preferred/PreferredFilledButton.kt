package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        modifier = modifier,
        enabled = enabled,
        content = content
    )
}

@Composable
fun PreferredFilledButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable (RowScope.() -> Unit),
) {
    PreferredFilledButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        if (isLoading) {
            PreferredCircularProgressIndicator()
        } else {
            content.invoke(this)
        }
    }
}
