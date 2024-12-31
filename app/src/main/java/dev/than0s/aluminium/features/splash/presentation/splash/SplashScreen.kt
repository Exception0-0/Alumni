package dev.than0s.aluminium.features.splash.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.AluminiumLottieAnimation
import dev.than0s.aluminium.core.presentation.utils.Screen

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
    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AluminiumLottieAnimation(
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
    SplashScreenContent()
}