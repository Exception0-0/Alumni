package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun AluminiumLoadingElevatedButton(
    label: String,
    circularProgressIndicatorState: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        enabled = !circularProgressIndicatorState,
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        modifier = modifier
    ) {
        if (circularProgressIndicatorState) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            Text(text = label)
        }
    }
}