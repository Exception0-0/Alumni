package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun PreferredLottieAnimation(
    lottieAnimation: Int,
    modifier: Modifier = Modifier,
    iteration: Int = LottieConstants.IterateForever,
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieAnimation)
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        iterations = iteration,
        isPlaying = true,
        modifier = modifier
    )
}
