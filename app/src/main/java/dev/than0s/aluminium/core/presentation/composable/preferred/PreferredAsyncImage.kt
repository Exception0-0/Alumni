package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerBackground

@Composable
fun PreferredAsyncImage(
    model: Any?,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    SubcomposeAsyncImage(
        model = model,
        loading = {
            ShimmerEffectAsyncImage()
        },
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
private fun ShimmerEffectAsyncImage() {
    ShimmerBackground(
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
    )
}