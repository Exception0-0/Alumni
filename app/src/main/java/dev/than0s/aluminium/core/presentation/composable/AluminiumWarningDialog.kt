package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AluminiumAlertDialog(
    title: String,
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
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun AluminiumAlertDialog(
    title: String,
    circularProgressIndicatorState: Boolean,
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
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            AluminiumLoadingTextButton(
                onClick = {
                    onConfirmation()
                },
                circularProgressIndicatorState = circularProgressIndicatorState,
                label = "Confirm"
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                content = {
                    Text("Dismiss")
                }
            )
        }
    )
}

@Preview
@Composable
private fun AluminiumWarningDialogPreview() {
    AluminiumAlertDialog(
        title = "Hello It is a warning",
        description = "write something to user like what we are going to do",
        onDismissRequest = {},
        onConfirmation = {}
    )
}