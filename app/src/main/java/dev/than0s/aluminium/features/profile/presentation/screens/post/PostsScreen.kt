package dev.than0s.aluminium.features.profile.presentation.screens.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerBackground
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.postHeight
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    PostsContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun PostsContent(
    screenState: PostsState,
    onEvent: (PostsEvents) -> Unit,
) {
    if (screenState.isLoading) {
        ShimmerEffectLoading(
            modifier = Modifier.height(350.dp)
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .height(350.dp),
            columns = GridCells.Adaptive(MaterialTheme.postHeight.small),
            content = {
                items(screenState.postList) {
                    PostImagePreview(
                        post = it
                    )
                }
            }
        )
    }
}

@Composable
private fun PostImagePreview(post: Post) {
    Box(
        modifier = Modifier
            .size(MaterialTheme.postHeight.small)
            .padding(MaterialTheme.padding.verySmall)
    ) {
        PreferredAsyncImage(
            model = post.file,
            contentDescription = "post image",
            modifier = Modifier.clip(
                RoundedCornerShape(MaterialTheme.roundedCorners.default)
            )
        )
    }
}

@Composable
private fun ShimmerEffectLoading(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MaterialTheme.postHeight.small),
        modifier = modifier
    ) {
        items(8) {
            PostShimmerCard()
        }
    }
}

@Composable
private fun PostShimmerCard() {
    ShimmerBackground(
        modifier = Modifier
            .shimmer()
            .size(MaterialTheme.postHeight.small)
            .padding(MaterialTheme.padding.verySmall)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PostsContent(
        screenState = PostsState(),
        onEvent = {}
    )
}