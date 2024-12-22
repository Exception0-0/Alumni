package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.than0s.aluminium.ui.roundCorners

@Composable
fun AluminiumElevatedButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        modifier = modifier
    ) {
        Text(text = label)
    }
}