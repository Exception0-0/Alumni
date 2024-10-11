package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.registration.presentation.screens.registration.admin

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    popAndOpen: (Screen) -> Unit
) {
    viewModel.loadScreen { role: String? ->
        when (role) {
            null -> popAndOpen(Screen.SignInScreen)
            admin -> popAndOpen(Screen.RegistrationRequestsScreen)
            else -> popAndOpen(Screen.PostsScreen())
        }
    }
}

@Composable
private fun SplashScreenContent() {
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenContent()
}