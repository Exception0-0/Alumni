package dev.than0s.aluminium.features.auth.presentation.screens.sign_out

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignOutScreen(
    viewModel: SignOutViewModel = hiltViewModel(),
    restartApp: () -> Unit
) {
    SignOutContent(
        onEvent = viewModel::onEvent,
        restartApp = restartApp
    )
}

@Composable
fun SignOutContent(
    onEvent: (SignOutEvents) -> Unit,
    restartApp: () -> Unit
) {
    onEvent(SignOutEvents.SignOut(onSuccess = restartApp))
}