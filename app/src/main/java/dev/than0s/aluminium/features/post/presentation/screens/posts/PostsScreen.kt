package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.preferred.ShimmerBackground
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize

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
    if (screenState.deletePostId != null) {
        PostDeleteDialog(
            isDeleting = screenState.isDeleting,
            onDismissRequest = {
                onEvent(PostsEvents.DismissPostDeleteDialog)
            },
            onConfirmation = {
                onEvent(PostsEvents.DeletePost)
            }
        )
    }
    PullToRefreshBox(
        isRefreshing = screenState.isLoading,
        onRefresh = {
            onEvent(PostsEvents.LoadPosts)
        },
    ) {
        if (screenState.isLoading) {
            ShimmerPostList()
        } else {
            LazyColumn(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
            ) {
                items(screenState.postList) { post ->
                    if (!userMap.containsKey(post.userId)) {
                        onEvent(PostsEvents.GetUser(post.userId))
                    }
                    if (!likeMap.containsKey(post.id)) {
                        onEvent(PostsEvents.GetLike(post.id))
                    }
                    PostBox(
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
                                )
                            )
                        },
                        onDeleteClick = {
                            onEvent(PostsEvents.ShowPostDeleteDialog(postId = post.id))
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
fun PostBox(
    post: Post,
    user: User?,
    likeStatus: Like?,
    onCommentClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLikeClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
        modifier = Modifier.padding(
            bottom = MaterialTheme.padding.small
        )
    ) {
        TopSection(
            user = user ?: User(),
            onProfileClick = onProfileClick,
            onDeleteClick = onDeleteClick
        )

        AluminiumAsyncImage(
            model = post.file,
            contentDescription = "${user?.firstName} ${user?.lastName}",
            onTapFullScreen = true,
            modifier = Modifier
                .height(450.dp)
        )

        BottomSection(
            isLiked = likeStatus != null,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
        )

        AluminiumDescriptionText(
            description = post.description,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.padding.medium
            )
        )

        Text(
            text = PrettyTimeUtils.getPrettyTime(post.timestamp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.padding.medium
            )
        )
    }
}

@Composable
private fun TopSection(
    user: User,
    onProfileClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ListItem(
        leadingContent = {
            AluminiumAsyncImage(
                model = user.profileImage,
                onTapFullScreen = true,
                modifier = Modifier
                    .size(MaterialTheme.profileSize.medium)
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
                        onClick = onDeleteClick
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
private fun BottomSection(
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = MaterialTheme.padding.small)
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

@Composable
private fun PostDeleteDialog(
    isDeleting: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.WarningAmber, contentDescription = "warning")
        },
        title = {
            Text(text = stringResource(R.string.post_delete))
        },
        text = {
            Text(text = stringResource(R.string.delete_alert_message))
        },
        onDismissRequest = {},
        confirmButton = {
            AluminiumLoadingTextButton(
                label = "Confirm",
                isLoading = isDeleting,
                onClick = {
                    onConfirmation()
                }
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                content = {
                    Text("Dismiss")
                },
                enabled = !isDeleting
            )
        }
    )
}

@Composable
private fun ShimmerPostList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 1..5) {
            PostBoxShimmer()
        }
    }
}

@Composable
private fun PostBoxShimmer() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
        modifier = Modifier
            .padding(bottom = MaterialTheme.padding.small)
            .shimmer()
    ) {
        ListItem(
            headlineContent = {
                ShimmerBackground(
                    modifier = Modifier
                        .height(16.dp)
                        .width(MaterialTheme.Size.medium)
                )
            },
            leadingContent = {
                ShimmerBackground(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(32.dp)
                )
            },
            trailingContent = {
                ShimmerBackground(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(16.dp)
                )
            }
        )
        ShimmerBackground(
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.padding.medium,
                    vertical = MaterialTheme.padding.small
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                modifier = Modifier
                    .align(Alignment.TopStart)
            ) {
                ShimmerBackground(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(16.dp)
                )
                ShimmerBackground(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                ShimmerBackground(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(16.dp)
                )
            }
        }
        ShimmerBackground(
            modifier = Modifier
                .height(16.dp)
                .width(MaterialTheme.Size.large)
                .padding(horizontal = MaterialTheme.padding.medium)
        )
        ShimmerBackground(
            modifier = Modifier
                .height(16.dp)
                .width(MaterialTheme.Size.medium)
                .padding(horizontal = MaterialTheme.padding.medium)
        )
        ShimmerBackground(
            modifier = Modifier
                .height(16.dp)
                .width(MaterialTheme.Size.small)
                .padding(horizontal = MaterialTheme.padding.medium)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
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
//    PostBoxShimmer()
}