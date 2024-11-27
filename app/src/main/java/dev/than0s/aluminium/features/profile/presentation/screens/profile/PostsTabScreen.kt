package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import dev.than0s.aluminium.core.composable.AluminiumCard
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.spacing

@Composable
fun PostsTabScreen(
    postsList: List<Post>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MaterialTheme.Size.default),
        content = {
            items(postsList) {
                PostPreviewCard(it.file)
            }
        }
    )
}

@Composable
fun PostPreviewCard(
    image: Uri
) {
    AluminiumCard(
        modifier = Modifier.size(
            MaterialTheme.Size.medium
        )
            .padding(MaterialTheme.spacing.extraSmall)
    ) {
        AsyncImage(
            model = image,
            contentDescription = "post image",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PostsTabScreen(
        postsList = listOf(
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY)
        )
    )
}