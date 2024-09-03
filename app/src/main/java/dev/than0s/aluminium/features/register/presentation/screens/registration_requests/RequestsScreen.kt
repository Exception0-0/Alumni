package dev.than0s.aluminium.features.register.presentation.screens.registration_requests

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
        items(items = requestsList) {
            Text(text = it.toString())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
    RegistrationRequestsContent(emptyList())
}