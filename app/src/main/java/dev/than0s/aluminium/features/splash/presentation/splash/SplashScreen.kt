package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    popAndOpen: (Screen) -> Unit
) {
    viewModel.onEvent(
        SplashScreenEvents.OnLoad(
            popAndOpen = popAndOpen
        )
    )
}

@Composable
private fun SplashScreenContent() {
    AluminiumTitleText(
        title = "Loading...",
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenContent()
}