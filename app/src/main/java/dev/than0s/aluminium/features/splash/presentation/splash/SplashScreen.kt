package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.Screen

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    popAndOpen: (Screen) -> Unit
) {
    viewModel.loadScreen { role: Role? ->
        when (role) {
            null -> popAndOpen(Screen.SignInScreen)
            Role.Admin -> popAndOpen(Screen.RegistrationRequestsScreen)
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