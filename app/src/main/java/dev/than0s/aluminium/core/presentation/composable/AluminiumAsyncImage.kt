package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumAsyncImage(
    model: Any?,
    settings: AluminiumAsyncImageSettings,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    isFullScreen: Boolean = false,
) {
    var _modifier = modifier

    if (isFullScreen) {
        var fullScreenState by rememberSaveable { mutableStateOf(false) }
        if (fullScreenState) {
            Dialog(
                onDismissRequest = {
                    fullScreenState = false
                },
                content = {
                    Surface(
                        color = Color.Black,
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        colors = TopAppBarDefaults.topAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            titleContentColor = MaterialTheme.colorScheme.primary,
                                        ),
                                        title = {},
                                        navigationIcon = {
                                            IconButton(
                                                onClick = {
                                                    fullScreenState = false
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                    contentDescription = "Localized description"
                                                )
                                            }
                                        }
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
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }
        _modifier = _modifier.clickable {
            fullScreenState = true
        }
    }

    SubcomposeAsyncImage(
        model = model,
        loading = {
            ShimmerBackground(
                modifier = modifier
                    .shimmer()
            )
        },
        contentScale = contentScale,
        contentDescription = settings.contentDescription,
        modifier = _modifier
    )
}

@Composable
private fun PinchZoomImage(
    model: Any?,
    modifier: Modifier
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
        AsyncImage(
            model = model,
            contentDescription = "pinch zoom image",
            modifier = Modifier
                .fillMaxSize()
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

sealed class AluminiumAsyncImageSettings(
    val contentDescription: String,
    val placeholder: Int,
    val error: Int
) {
    data object UserProfile : AluminiumAsyncImageSettings(
        contentDescription = "user profile image",
        placeholder = R.drawable.ic_launcher_background,
        error = R.drawable.ic_launcher_background,
    )

    data object PostAddImage : AluminiumAsyncImageSettings(
        contentDescription = "post image",
        placeholder = R.drawable.baseline_add_a_photo_24,
        error = R.drawable.baseline_add_a_photo_24,
    )

    data object PostImage : AluminiumAsyncImageSettings(
        contentDescription = "post image",
        placeholder = R.drawable.ic_launcher_background,
        error = R.drawable.ic_launcher_background,
    )

    data object CoverImage : AluminiumAsyncImageSettings(
        contentDescription = "cover image",
        placeholder = R.drawable.ic_launcher_background,
        error = R.drawable.ic_launcher_background,
    )
}

data object ProfileImageModifier {
    val small: Modifier = Modifier
        .size(20.dp)
        .clip(CircleShape)

    val medium: Modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)

    val large: Modifier = Modifier
        .size(80.dp)
        .clip(CircleShape)
}

data object PostImageModifier {
    val default: Modifier = Modifier
        .height(450.dp)
        .width(360.dp)
        .clip(RoundedCornerShape(8.dp))
}

data object CoverImageModifier {
    val default: Modifier = Modifier
        .height(200.dp)
        .width(360.dp)
}