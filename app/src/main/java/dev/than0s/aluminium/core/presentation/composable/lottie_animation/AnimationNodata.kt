package dev.than0s.aluminium.core.presentation.composable.lottie_animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredLottieAnimation

@Composable
fun AnimationNoData(text: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredColumn(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            PreferredLottieAnimation(
                lottieAnimation = R.raw.empty_box_animation,
                iteration = 1,
                modifier = Modifier
                    .size(150.dp)
            )
            Text(
                text = text
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
            )
        }
    }
}