package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            items(screenState.postList) { post ->
                AluminiumElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    PostCard(
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
fun PostCard(
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

        UserDetail(
            user = user ?: User(),
            modifier = Modifier.clickable {
                openScreen(Screen.ProfileScreen(post.userId))
            }
        )

        AluminiumTitleText(
            title = post.title,
        )

        AluminiumDescriptionText(
            description = post.description,
        )

        AluminiumAsyncImage(
            model = post.file,
            onTapFullScreen = true,
            modifier = Modifier
                .height(450.dp)
                .width(360.dp)
        )
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

@Composable
private fun UserDetail(
    user: User,
    modifier: Modifier
) {
    AluminiumCard {
        ListItem(
            headlineContent = {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                )
            },
            leadingContent = {
                AluminiumAsyncImage(
                    model = user.profileImage,
                    onTapFullScreen = true,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            },
            supportingContent = {
                AluminiumDescriptionText(
                    description = "10:30pm"
                )
            },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            modifier = modifier
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
//    AluminiumCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
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
                onClick = {
                    openScreen(Screen.CommentsScreen(postId))
                },
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Comment,
                        contentDescription = "comment button",
                    )
                }
            )
        }
//    }
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