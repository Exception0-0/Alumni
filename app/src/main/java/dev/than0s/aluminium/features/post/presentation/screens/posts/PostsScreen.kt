package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminumCircularLoading
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.spacing

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
        AluminumCircularLoading()
    } else {
        LazyColumn {
            items(screenState.postList) { post ->
                if (!userMap.containsKey(post.userId)) {
                    onEvent(PostsEvents.GetUser(post.userId))
                }
                if (!likeMap.containsKey(post.id)) {
                    onEvent(PostsEvents.GetLike(post.id))
                }
                PostCard(
                    post = post,
                    user = userMap[post.userId],
                    likeStatus = likeMap[post.id],
                    onEvent = onEvent,
                    onCommentClick = {
                        openScreen(Screen.CommentsScreen(post.id))
                    },
                    onProfileClick = {
                        openScreen(Screen.ProfileScreen(post.userId))
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun PostCard(
    post: Post,
    user: User?,
    likeStatus: Like?,
    onCommentClick: () -> Unit,
    onProfileClick: () -> Unit,
    onEvent: (PostsEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        modifier = modifier.padding(
            vertical = MaterialTheme.spacing.small
        )
    ) {
        UserDetail(
            user = user ?: User(),
            onProfileClick = onProfileClick
        )

        AluminiumAsyncImage(
            model = post.file,
            onTapFullScreen = true,
            modifier = Modifier
                .height(450.dp)
        )

        PostStatus(
            postId = post.id,
            isLiked = likeStatus != null,
            onEvent = onEvent,
            onCommentClick = onCommentClick,
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.small
            )
        )

        AluminiumDescriptionText(
            description = post.description,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.medium
            )
        )

        Text(
            text = "10:30pm",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.medium
            )
        )
    }
}

@Composable
private fun UserDetail(
    user: User,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        leadingContent = {
            AluminiumAsyncImage(
                model = user.profileImage,
                onTapFullScreen = true,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
        },
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
            )
        },
        modifier = modifier.clickable {
            onProfileClick()
        }
    )
}

@Composable
private fun PostStatus(
    postId: String,
    isLiked: Boolean,
    onEvent: (PostsEvents) -> Unit,
    onCommentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
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

        IconButton(
            onClick = onCommentClick,
            content = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Comment,
                    contentDescription = "comment button",
                )
            }
        )
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