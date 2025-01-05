package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerBackground
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredAsyncImage(
    model: Any?,
    shape: Shape = RoundedCornerShape(MaterialTheme.roundedCorners.none),
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
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
private fun ShimmerEffectAsyncImage(shape: Shape) {
    ShimmerBackground(
        shape = shape,
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
    )
}