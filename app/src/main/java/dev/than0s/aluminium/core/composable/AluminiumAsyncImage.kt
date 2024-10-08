package dev.than0s.aluminium.core.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.than0s.aluminium.R

@Composable
fun AluminiumAsyncImage(
    model: Any?,
    settings: AluminiumAsyncImageSettings,
    modifier: Modifier
) {
    AsyncImage(
        model = model,
        placeholder = painterResource(settings.placeholder),
        error = painterResource(settings.error),
        contentScale = ContentScale.Crop,
        contentDescription = settings.contentDescription,
        modifier = modifier
    )
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

    data object PostImage : AluminiumAsyncImageSettings(
        contentDescription = "post image",
        placeholder = R.drawable.ic_launcher_background,
        error = R.drawable.ic_launcher_background,
    )

    data object CoverImage : AluminiumAsyncImageSettings(
        contentDescription = "conver image",
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
        .clip(RoundedCornerShape(16.dp))
}

data object CoverImageModifier {
    val default: Modifier = Modifier
        .height(200.dp)
        .width(360.dp)
}