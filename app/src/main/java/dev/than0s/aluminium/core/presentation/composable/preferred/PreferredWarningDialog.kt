package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PreferredWarningDialog(
    title: String,
    isLoading: Boolean,
    description: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
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
        text = {
            Text(text = description)
        },
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
                onClick = {
                    onDismissRequest()
                },
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