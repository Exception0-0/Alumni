package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredWarningDialog(
    title: String,
    isLoading: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    description: String? = null,
    shape: Shape = RoundedCornerShape(roundedCorners.default)
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = Icons.Default.Warning.name
            )
        },
        title = {
            Text(text = title)
        },
        text = if (description != null) {
            {
                Text(
                    text = description,
                    textAlign = TextAlign.Center
                )
            }
        } else null,
        shape = shape,
        onDismissRequest = {},
        confirmButton = {
            PreferredTextButton(
                onClick = onConfirmation,
                isLoading = isLoading,
                text = "Ok"
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                enabled = !isLoading,
                content = {
                    Text("Cancel")
                }
            )
        }
    )
}

@Composable
fun PreferredWarningDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    description: String? = null,
    shape: Shape = RoundedCornerShape(roundedCorners.default)
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = Icons.Default.Warning.name
            )
        },
        title = {
            Text(text = title)
        },
        text = if (description != null) {
            {
                Text(
                    text = description,
                    textAlign = TextAlign.Center
                )
            }
        } else null,
        shape = shape,
        onDismissRequest = {},
        confirmButton = {
            PreferredTextButton(
                onClick = onConfirmation,
                text = "Ok"
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                content = {
                    Text("Cancel")
                }
            )
        }
    )
}


@Preview
@Composable
private fun PreferredWarningDialogPreview() {
    PreferredWarningDialog(
        title = "Hello It is a warning",
        isLoading = false,
        description = "write something to user like what we are going to do",
        onDismissRequest = {},
        onConfirmation = {}
    )
}