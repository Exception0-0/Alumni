package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredLottieAnimation
import dev.than0s.aluminium.core.presentation.utils.Screen

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    replaceScreen: (Screen) -> Unit
) {
    SplashScreenContent(
        replaceScreen = replaceScreen,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun SplashScreenContent(
    replaceScreen: (Screen) -> Unit,
    onEvent: (SplashScreenEvents) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onEvent(
            SplashScreenEvents.OnLoad(
                replaceScreen = replaceScreen
            )
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredLottieAnimation(
            lottieAnimation = R.raw.waiting_animation,
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenContent(
        replaceScreen = {},
        onEvent = {}
    )
}