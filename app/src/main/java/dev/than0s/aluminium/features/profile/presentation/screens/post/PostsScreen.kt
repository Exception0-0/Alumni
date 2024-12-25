package dev.than0s.aluminium.features.profile.presentation.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.presentation.composable.AluminiumCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.composable.PostImageModifier
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.presentation.composable.AluminumLoading
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel(),
    userId: String,
    openScreen: (Screen) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.userId = userId
        viewModel.onEvent(PostsEvents.LoadPosts)
    }

    PostsContent(
        screenState = viewModel.screenState,
        likeMap = viewModel.likeMap,
        openScreen = openScreen,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun PostsContent(
    screenState: PostsState,
    likeMap: Map<String, Like?>,
    openScreen: (Screen) -> Unit,
    onEvent: (PostsEvents) -> Unit,
) {
    if (screenState.postDialog != null) {
        Dialog(
            onDismissRequest = {
                onEvent(PostsEvents.OnPostDialogDismissRequest)
            },
            content = {
                AluminiumElevatedCard {
                    PostDetailCard(
                        post = screenState.postList.find { it.id == screenState.postDialog }!!,
                        onEvent = onEvent,
                        likeMap = likeMap,
                        openScreen = openScreen
                    )
                }
            }
        )
    }

    if (screenState.isLoading) {
        AluminumLoading()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(MaterialTheme.Size.default),
            content = {
                items(screenState.postList) {
                    PostPreviewCard(
                        post = it,
                        onClick = { postId ->
                            onEvent(PostsEvents.OnPostClick(postId))
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun PostPreviewCard(
    post: Post,
    onClick: (String) -> Unit
) {
    AluminiumCard(
        onClick = {
            onClick(post.id)
        },
        modifier = Modifier
            .size(
                MaterialTheme.Size.medium
            )
            .padding(MaterialTheme.spacing.extraSmall)
    ) {
        AluminiumAsyncImage(
            model = post.file,
            settings = AluminiumAsyncImageSettings.PostImage,
            isFullScreen = false,
            modifier = Modifier
        )
    }
}

@Composable
private fun PostDetailCard(
    post: Post,
    likeMap: Map<String, Like?>,
    openScreen: (Screen) -> Unit,
    onEvent: (PostsEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    onEvent(PostsEvents.GetLike(post.id))
    val like = likeMap[post.id]

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxWidth()
    ) {

        AluminiumTitleText(
            title = post.title,
        )

        AluminiumDescriptionText(
            description = post.description,
        )

        AluminiumAsyncImage(
            model = post.file,
            settings = AluminiumAsyncImageSettings.PostImage,
            modifier = PostImageModifier.default
        )

        AluminiumCard {
            PostStatus(
                postId = post.id,
                isLiked = like != null,
                onEvent = onEvent,
                openScreen = openScreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small)
            )
        }
    }
}

//@Composable
//private fun ShimmerPostCard() {
//    AluminiumCard(
//        modifier = Modifier
//            .size(MaterialTheme.Size.medium)
//            .padding(MaterialTheme.spacing.extraSmall)
//            .shimmer(),
//        content = {}
//    )
//}
//
//@Composable
//private fun ShimmerPostList() {
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(MaterialTheme.Size.default),
//        content = {
//            items(10) {
//                ShimmerPostCard()
//            }
//        }
//    )
//}

@Composable
private fun PostStatus(
    postId: String,
    isLiked: Boolean,
    onEvent: (PostsEvents) -> Unit,
    openScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            IconButton(
                content = {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                        contentDescription = "like button"
                    )
                },
                onClick = {
                    onEvent(PostsEvents.OnLikeClick(postId, isLiked))
                },
            )
            AluminiumTitleText(
                title = "Like",
                fontSize = MaterialTheme.textSize.small
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            IconButton(onClick = {
                openScreen(Screen.CommentsScreen(postId))
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Comment,
                    contentDescription = "comment button",
                )
            }
            AluminiumTitleText(
                title = "Comments",
                fontSize = MaterialTheme.textSize.small
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PostsContent(
        screenState = PostsState(),
        likeMap = emptyMap(),
        openScreen = {},
        onEvent = {}
    )
}