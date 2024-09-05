package dev.than0s.aluminium.features.register.presentation.screens.registration_requests

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.than0s.aluminium.core.data_class.RegistrationForm

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
    ElevatedCard(onClick = { /*TODO*/ }) {
        Text(request.toString())
        Row {
            ElevatedButton(onClick = {
                // todo alert dialog
                onAcceptedClick(request)
            }) {
                Text("Accept")
            }
            ElevatedButton(onClick = {
                // todo alert dialog
                onRejectedClick(request)
            }) {
                Text("Reject")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
    RegistrationRequestsContent(emptyList(), {}, {})
}