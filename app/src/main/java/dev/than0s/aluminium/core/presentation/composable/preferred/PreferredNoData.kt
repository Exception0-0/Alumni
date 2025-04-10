package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import dev.than0s.aluminium.R
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

@Composable
fun PreferredNoData(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    PreferredCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        ),
        modifier = modifier
    ) {
        PreferredColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            modifier = Modifier
                .padding(MaterialTheme.padding.medium)
                .width(180.dp)
        ) {
            Text(
                text = title
                    .lowercase()
                    .replaceFirstChar {
                        it.uppercase()
                    },
                style = MaterialTheme.typography.titleLarge
            )
            description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
            PreferredLottieAnimation(
                lottieAnimation = R.raw.emoji_animation,
                iteration = LottieConstants.IterateForever,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
            )
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