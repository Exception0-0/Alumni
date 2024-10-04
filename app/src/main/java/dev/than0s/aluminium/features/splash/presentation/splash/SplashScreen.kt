package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    popAndOpen: (Screen) -> Unit
) {
    viewModel.loadScreen { hasUser: Boolean ->
        if (hasUser) {
            popAndOpen(Screen.PostsScreen())
        } else {
            popAndOpen(Screen.SignInScreen)
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