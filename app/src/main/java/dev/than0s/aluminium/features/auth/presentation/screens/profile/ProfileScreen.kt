package dev.than0s.aluminium.features.auth.presentation.screens.profile

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), restartApp: () -> Unit) {
    ProfileScreenContent(
        onSignOutClick = viewModel::onSignOutClick,
        restartApp = restartApp
    )
}

@Composable
private fun ProfileScreenContent(onSignOutClick: (() -> Unit) -> Unit, restartApp: () -> Unit) {
    ElevatedButton(
        onClick = {
            onSignOutClick(restartApp)
        }
    ) {

    }
    Text(text = "sign out")
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent({}, {})
}