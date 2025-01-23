package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import dev.than0s.aluminium.R
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

@Composable
fun PreferredNoData(
    title: String,
    description: String? = null,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            ),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            PreferredColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(MaterialTheme.padding.medium)
            ) {
                Text(
                    text = title
                        .lowercase()
                        .replaceFirstChar {
                            it.uppercase()
                        },
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.textSize.large
                )
                description?.let {
                    Spacer(modifier = Modifier.size(MaterialTheme.padding.small))
                    Text(
                        text = it,
                        fontSize = MaterialTheme.textSize.medium
                    )
                }
                PreferredLottieAnimation(
                    lottieAnimation = R.raw.emoji_animation,
                    iteration = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(180.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreferredNoData(
        title = "No Data",
        description = "No data to show do something"
    )
}