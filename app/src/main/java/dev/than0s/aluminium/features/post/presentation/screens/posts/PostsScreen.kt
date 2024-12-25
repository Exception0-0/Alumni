package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.presentation.composable.AluminiumCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.composable.PostImageModifier
import dev.than0s.aluminium.core.presentation.composable.ProfileImageModifier
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.core.presentation.composable.ShimmerCircularBackground
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.presentation.composable.AluminumLoading
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    PostsScreenContent(
        screenState = viewModel.screenState,
        userMap = viewModel.userMap,
        likeMap = viewModel.likeMap,
        onEvent = viewModel::onEvent,
        openScreen = openScreen
    )
}

@Composable
private fun PostsScreenContent(
    screenState: PostsState,
    userMap: Map<String, User>,
    likeMap: Map<String, Like?>,
    onEvent: (PostsEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    if (screenState.isLoading) {
        AluminumLoading()
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            items(screenState.postList) { post ->
                AluminiumElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    PostItem(
                        post = post,
                        userMap = userMap,
                        likeMap = likeMap,
                        onEvent = onEvent,
                        openScreen = openScreen,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}


@Composable
fun PostItem(
    post: Post,
    userMap: Map<String, User>,
    likeMap: Map<String, Like?>,
    openScreen: (Screen) -> Unit,
    onEvent: (PostsEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    onEvent(PostsEvents.GetUser(post.userId))
    onEvent(PostsEvents.GetLike(post.id))
    val user = userMap[post.userId]
    val like = likeMap[post.id]

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .width(360.dp)
    ) {

        AluminiumCard(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(MaterialTheme.roundCorners.default))
                .clickable {
                    openScreen(Screen.ProfileScreen(post.userId))
                }
        ) {
            UserDetail(
                user = user ?: User(),
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
            )
        }

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

@Composable
private fun UserDetail(
    user: User,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AluminiumAsyncImage(
            model = user.profileImage,
            settings = AluminiumAsyncImageSettings.UserProfile,
            modifier = ProfileImageModifier.medium
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        AluminiumTitleText(
            title = "${user.firstName} ${user.lastName}",
            fontSize = MaterialTheme.textSize.large
        )
    }
}

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
private fun PostsScreenPreview() {
    PostsScreenContent(
        screenState = PostsState(),
        userMap = emptyMap(),
        likeMap = emptyMap(),
        onEvent = {},
        openScreen = {}
    )
}