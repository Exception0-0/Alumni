package dev.than0s.aluminium.features.auth.presentation.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.Screen

@Composable
fun SplashScreen(viewModel: SplashViewModel = hiltViewModel(), popAndOpen: (String) -> Unit) {
    SplashScreenContent(
        loadAccount = viewModel::loadAccount,
        popAndOpen = popAndOpen
    )
}

@Composable
private fun SplashScreenContent(
    loadAccount: ((Boolean) -> Unit) -> Unit,
    popAndOpen: (String) -> Unit
) {
    loadAccount { hasUser: Boolean ->
        if (hasUser) {
            popAndOpen("")
        } else {
            popAndOpen(Screen.SignInScreen.route)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenContent({}, {})
}