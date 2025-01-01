package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize


@Composable
fun CardInfoFormat(
    title: String,
    info: String,
    icon: ImageVector,
) {
    AluminiumElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                AluminiumTitleText(
                    title = title,
                    fontSize = MaterialTheme.textSize.large
                )

                AluminiumDescriptionText(
                    description = info,
                )
            }
        }
    }
}

@Composable
fun CardInfoFormatShimmer() {
    AluminiumElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            ShimmerBackground(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(16.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                ShimmerBackground(
                    modifier = Modifier
                        .height(16.dp)
                        .width(MaterialTheme.Size.small)
                )

                ShimmerBackground(
                    modifier = Modifier
                        .height(16.dp)
                        .width(MaterialTheme.Size.large)
                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    CardInfoFormatShimmer()
}