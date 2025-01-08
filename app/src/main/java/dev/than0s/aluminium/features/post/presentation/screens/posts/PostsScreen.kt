package dev.than0s.aluminium.features.post.presentation.screens.posts

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFullScreen
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredPinchZoom
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWarningDialog
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWrappedText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerBackground
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerIcons
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerProfileImage
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextWidth
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.postHeight
import dev.than0s.aluminium.ui.profileSize
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
        PreferredWarningDialog(
            title = stringResource(R.string.post_delete),
            description = stringResource(R.string.alert_message),
            isLoading = screenState.isDeleting,
            onDismissRequest = {
                onEvent(PostsEvents.DismissPostDeleteDialog)
            },
            onConfirmation = {
                onEvent(PostsEvents.DeletePost)
            }
        )
    }
    if (screenState.fullScreenImage != null) {
        PreferredFullScreen(
            contentDescription = "Post Image",
            onDismissRequest = {
                onEvent(PostsEvents.DismissFullScreenImage)
            },
            content = {
                PreferredPinchZoom {
                    PreferredAsyncImage(
                        model = screenState.fullScreenImage,
                        contentScale = ContentScale.Fit,
                        contentDescription = "Post Image",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
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
                modifier = Modifier.fillMaxWidth()
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
                            openScreen(
                                Screen.CommentsScreen(postId = post.id)
                            )
                        },
                        onProfileClick = {
                            openScreen(
                                Screen.ProfileScreen(userId = post.userId)
                            )
                        },
                        onLikeClick = {
                            onEvent(
                                PostsEvents.OnLikeClick(
                                    postId = post.id,
                                )
                            )
                        },
                        onDeleteClick = {
                            onEvent(
                                PostsEvents.ShowPostDeleteDialog(
                                    postId = post.id
                                )
                            )
                        },
                        onPostImageClick = { uri ->
                            onEvent(
                                PostsEvents.ShowFullScreenImage(uri)
                            )
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBox(
    post: Post,
    user: User?,
    likeStatus: Like?,
    onCommentClick: () -> Unit,
    onPostImageClick: (Uri) -> Unit,
    onProfileClick: () -> Unit,
    onLikeClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
    ) {
        TopSection(
            user = user ?: User(),
            timestamp = post.timestamp,
            onProfileClick = onProfileClick,
            onDeleteClick = onDeleteClick
        )

        val configuration = LocalConfiguration.current
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { post.files.size },
            preferredItemWidth = configuration.screenWidthDp.dp,
        ) { index ->
            val uri = post.files[index]
            PreferredAsyncImage(
                model = uri,
                contentDescription = "${user?.firstName} ${user?.lastName}",
                modifier = Modifier
                    .height(MaterialTheme.postHeight.default)
                    .clickable {
                        onPostImageClick(uri)
                    }
            )
        }

        BottomSection(
            post = post,
            isLiked = likeStatus != null,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
        )
    }
}

@Composable
private fun TopSection(
    user: User,
    timestamp: Long,
    onProfileClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ListItem(
        leadingContent = {
            PreferredAsyncImage(
                model = user.profileImage,
                shape = CircleShape,
                modifier = Modifier
                    .size(MaterialTheme.profileSize.medium)
            )
        },
        headlineContent = {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontSize = MaterialTheme.textSize.medium,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            Text(
                text = PrettyTimeUtils.getPrettyTime(timestamp),
                fontSize = MaterialTheme.textSize.medium,
                modifier = Modifier.fillMaxWidth()
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
    post: Post,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
        modifier = Modifier.padding(horizontal = MaterialTheme.padding.small)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PreferredRow(
                horizontalArrangement = Arrangement.Start,
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

            PreferredRow(
                horizontalArrangement = Arrangement.End,
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

        PreferredWrappedText(
            text = post.caption,
            fontSize = MaterialTheme.textSize.medium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ShimmerPostList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 1..5) {
            ShimmerPostBox()
        }
    }
}

@Composable
private fun ShimmerPostBox() {
    PreferredColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
        modifier = Modifier
            .shimmer()
    ) {
        ListItem(
            headlineContent = {
                ShimmerText()
            },
            supportingContent = {
                PreferredColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    VerticalDivider(
                        thickness = 0.dp,
                        modifier = Modifier.height(MaterialTheme.padding.extraSmall)
                    )
                    ShimmerText(
                        width = ShimmerTextWidth.small
                    )
                }
            },
            leadingContent = {
                ShimmerProfileImage()
            },
            trailingContent = {
                ShimmerIcons()
            }
        )
        ShimmerBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.postHeight.default)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.padding.small)
        ) {
            PreferredRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                modifier = Modifier
                    .align(Alignment.TopStart)
            ) {
                ShimmerIcons()
                ShimmerIcons()
            }
            PreferredRow(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                ShimmerIcons()
            }
        }
        ShimmerText(
            width = ShimmerTextWidth.high,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.small)
        )
        ShimmerText(
            width = ShimmerTextWidth.medium,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.small)
        )
        ShimmerText(
            width = ShimmerTextWidth.small,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.small)
        )
        HorizontalDivider()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PostsScreenPreview() {
//    PostsScreenContent(
//        screenState = PostsState(),
//        userMap = emptyMap(),
//        likeMap = emptyMap(),
//        onEvent = {},
//        openScreen = {}
//    )
    ShimmerPostBox()
}