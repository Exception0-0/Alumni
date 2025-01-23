package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerBackground
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredAsyncImage(
    model: Any?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(MaterialTheme.roundedCorners.none),
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    SubcomposeAsyncImage(
        model = model,
        loading = {
            ShimmerEffectAsyncImage(shape = shape)
        },
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier.clip(shape = shape)
    )
}

@Composable
fun PreferredAddPicture(
    model: Any?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(MaterialTheme.roundedCorners.none),
    contentDescription: String? = null,
    enabled: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    onRemovePicture: (() -> Unit)? = null,
    onAddPicture: () -> Unit,
) {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.none)
    ) {
        if (model == null) {
            PreferredSurface(
                shape = shape,
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = modifier.clickable(onClick = onAddPicture, enabled = enabled)
            ) {
                Image(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = "add picture",
                )
            }
        } else {
            SubcomposeAsyncImage(
                model = model,
                loading = {
                    ShimmerEffectAsyncImage(shape = shape)
                },
                contentScale = contentScale,
                contentDescription = contentDescription,
                modifier = modifier
                    .clip(shape = shape)
                    .clickable(onClick = onAddPicture, enabled = enabled)
            )
        }
        PreferredRow {
            if (model == null) {
                PreferredTextButton(
                    text = "Add picture",
                    onClick = onAddPicture,
                    enabled = enabled
                )
            } else {
                PreferredTextButton(
                    text = "Change",
                    onClick = onAddPicture,
                    enabled = enabled
                )
                if (onRemovePicture != null) {
                    PreferredTextButton(
                        text = "Remove",
                        onClick = onRemovePicture,
                        enabled = enabled
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerEffectAsyncImage(shape: Shape) {
    ShimmerBackground(
        shape = shape,
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreferredAddPicture(
        model = "",
        onAddPicture = {},
        onRemovePicture = {},
        shape = CircleShape,
        modifier = Modifier.size(MaterialTheme.profileSize.large)
    )
}