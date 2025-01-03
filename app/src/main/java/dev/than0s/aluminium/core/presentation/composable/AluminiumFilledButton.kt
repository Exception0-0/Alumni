package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun AluminiumFilledButton(
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
fun AluminiumLoadingFilledButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable (RowScope.() -> Unit),
) {
    AluminiumFilledButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = if (isLoading) {
            {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                )
            }
        } else {
            content
        }
    )
}

@Preview
@Composable
private fun AluminiumFilledButtonPreview() {
    AluminiumLoadingFilledButton(
        onClick = {},
        isLoading = true,
        content = {
            Text("Button")
        }
    )
}

