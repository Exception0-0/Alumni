package dev.than0s.aluminium.core.presentation.composable.lottie_animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredLottieAnimation
import dev.than0s.aluminium.ui.padding

@Composable
fun AnimationNoData(text: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            PreferredColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(MaterialTheme.padding.medium)
            ) {
                Text(
                    text = text
                        .lowercase()
                        .replaceFirstChar { it.uppercase() }
                )
                PreferredLottieAnimation(
                    lottieAnimation = R.raw.empty_box_animation,
                    iteration = 1,
                    modifier = Modifier
                        .size(150.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    AnimationNoData("No Data")
}