package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminumCircularLoading
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
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

@OptIn(ExperimentalMaterial3Api::class)
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
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = {
                onEvent(PostsEvents.LoadPosts)
            },
        ) {
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
                        onCommentClick = {
                            openScreen(Screen.CommentsScreen(post.id))
                        },
                        onProfileClick = {
                            openScreen(Screen.ProfileScreen(post.userId))
                        },
                        onLikeClick = {
                            onEvent(
                                PostsEvents.OnLikeClick(
                                    postId = post.id,
                                    hasLike = likeMap[post.id] != null
                                )
                            )
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
    onLikeClick: () -> Unit,
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
            contentDescription = "${user?.firstName} ${user?.lastName}",
            onTapFullScreen = true,
            modifier = Modifier
                .height(450.dp)
        )

        PostStatus(
            isLiked = likeStatus != null,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
        )

        AluminiumDescriptionText(
            description = post.description,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.medium
            )
        )
        Text(
            text = PrettyTimeUtils.getPrettyTime(post.timestamp),
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
    var expanded by remember { mutableStateOf(false) }
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
        trailingContent = {
            Box {
                IconButton(
                    onClick = {
                        expanded = !expanded
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "more"
                        )
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Profile")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile"
                            )
                        },
                        onClick = onProfileClick
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Delete")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        },
                        enabled = user.id == currentUserId,
                        onClick = {}
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Report")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = "Report"
                            )
                        },
                        onClick = {}
                    )
                }
            }
        },
        modifier = modifier.clickable {
            onProfileClick()
        }
    )
}

@Composable
private fun PostStatus(
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = MaterialTheme.spacing.small)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
        ) {
            IconButton(
                content = {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                        contentDescription = "like button"
                    )
                },
                onClick = onLikeClick,
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

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {

            IconButton(
                content = {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "save button"
                    )
                },
                onClick = {},
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