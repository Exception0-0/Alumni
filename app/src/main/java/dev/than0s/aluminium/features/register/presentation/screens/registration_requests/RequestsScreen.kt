package dev.than0s.aluminium.features.register.presentation.screens.registration_requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.mydiary.ui.elevation
import dev.than0s.mydiary.ui.spacing

@Composable
fun RegistrationRequestsScreen(viewModel: RequestViewModel = hiltViewModel()) {
    val requestsList = viewModel.requestsList.collectAsStateWithLifecycle(emptyList())
    RegistrationRequestsContent(
        requestsList = requestsList.value,
        onAcceptedClick = viewModel::onAcceptClick,
        onRejectedClick = viewModel::onRejectedClick
    )
}

@Composable
private fun RegistrationRequestsContent(
    requestsList: List<RegistrationForm>,
    onAcceptedClick: (RegistrationForm) -> Unit,
    onRejectedClick: (RegistrationForm) -> Unit
) {
    LazyColumn {
        items(items = requestsList) { request ->
            RegistrationRequestItem(request, onAcceptedClick, onRejectedClick)
        }
    }
}

@Composable
private fun RegistrationRequestItem(
    request: RegistrationForm,
    onAcceptedClick: (RegistrationForm) -> Unit,
    onRejectedClick: (RegistrationForm) -> Unit
) {
    var showAcceptAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showRejectAlertDialog by rememberSaveable { mutableStateOf(false) }

    if (showAcceptAlertDialog) {
        CustomAlertDialog(
            title = accepted_title,
            text = accepted_text,
            onDismissRequest = { showAcceptAlertDialog = false },
            onConfirmation = {
                onAcceptedClick(request)
                showAcceptAlertDialog = false
            }
        )
    }

    if (showRejectAlertDialog) {
        CustomAlertDialog(
            title = rejected_title,
            text = rejected_text,
            onDismissRequest = { showRejectAlertDialog = false },
            onConfirmation = {
                onRejectedClick(request)
                showRejectAlertDialog = false
            }
        )
    }

    ElevatedCard(
        modifier = Modifier.padding(
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.small
        )
    ) {
        Text(request.toString())
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small)
        ) {
            FloatingActionButton(
                onClick = {
                    showAcceptAlertDialog = true
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                elevation = FloatingActionButtonDefaults.elevation(MaterialTheme.elevation.none)
            ) {
                Icon(Icons.Default.Check, contentDescription = Icons.Default.Check.name)
            }

            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

            FloatingActionButton(
                onClick = {
                    showRejectAlertDialog = true
                },
                elevation = FloatingActionButtonDefaults.elevation(MaterialTheme.elevation.none)
            ) {
                Icon(Icons.Default.Clear, contentDescription = Icons.Default.Clear.name)
            }
        }
    }
}

@Composable
private fun CustomAlertDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.Warning, contentDescription = Icons.Default.Warning.name)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
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

const val accepted_title = "Do you really want to accept this request?"
const val accepted_text = "you can't undo this action, so be careful with your choices."

const val rejected_title = "Do you really want to reject this request?"
const val rejected_text = "You can't undo this action, so be careful with your choices."

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
    RegistrationRequestsContent(listOf(
        RegistrationForm(),
        RegistrationForm(),
        RegistrationForm()
    ), {}, {})
}