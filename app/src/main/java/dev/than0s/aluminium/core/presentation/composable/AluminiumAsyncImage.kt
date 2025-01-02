package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumAsyncImage(
    model: Any?,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    onTapFullScreen: Boolean = false,
) {
    var _modifier = modifier

    if (onTapFullScreen) {
        var fullScreenState by rememberSaveable { mutableStateOf(false) }

        if (fullScreenState) {
            ModalBottomSheet(
                onDismissRequest = {
                    fullScreenState = false
                },
                sheetState = rememberModalBottomSheetState(true, { false }),
                dragHandle = null,
                content = {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    contentDescription?.let {
                                        Text(it)
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        fullScreenState = false
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "back button"
                                        )
                                    }
                                },
                            )
                        }
                    ) { contentPadding ->
                        PinchZoomImage(
                            model = model,
                            modifier = Modifier.padding(contentPadding)
                        )
                    }
                }
            )
        }
        _modifier = _modifier.clickable {
            fullScreenState = true
        }
    }

    SubcomposeAsyncImage(
        model = model,
        loading = {
            AsyncImageShimmerEffect()
        },
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = _modifier
    )
}


@Composable
private fun PinchZoomImage(
    model: Any?,
    modifier: Modifier = Modifier
) {
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {
        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale * zoomChange).coerceIn(1f, 10f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }
        SubcomposeAsyncImage(
            model = model,
            loading = {
                AsyncImageShimmerEffect()
            },
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state)
        )
    }
}

@Composable
private fun AsyncImageShimmerEffect() {
    ShimmerBackground(
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
    )
}