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
        requestsList = requestsList.value
    )
}

@Composable
private fun RegistrationRequestsContent(
    requestsList: List<RegistrationForm>
) {
    LazyColumn {
        items(items = requestsList) { request ->
            RegistrationRequestItem(request)
        }
    }
}

@Composable
private fun RegistrationRequestItem(request: RegistrationForm) {
    ElevatedCard(onClick = { /*TODO*/ }) {
        Text(request.toString())
        Row {
            ElevatedButton(onClick = {}) {
                Text("Accept")
            }
            ElevatedButton(onClick = { /*TODO*/ }) {
                Text("Reject")
            }
        }
    }
}   

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
    RegistrationRequestsContent(emptyList())
}