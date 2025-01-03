package dev.than0s.aluminium.features.post.presentation.screens.comments

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import dev.than0s.aluminium.core.domain.data_class.Comment
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumLottieAnimation
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

@Composable
fun CommentScreen(
    viewModel: CommentsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    CommentScreenContent(
        screenState = viewModel.screenState,
        userMap = viewModel.userMap,
        onEvent = viewModel::onEvent,
        openScreen = openScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommentScreenContent(
    screenState: CommentState,
    userMap: Map<String, User>,
    onEvent: (CommentEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    if (screenState.deleteCommentId != null) {
        CommentDeleteDialog(
            isDeleting = screenState.isDeleting,
            onDismissRequest = {
                onEvent(CommentEvents.DismissCommentDeleteDialog)
            },
            onConfirmation = {
                onEvent(CommentEvents.DeleteComment)
            }
        )
    }

    PullToRefreshBox(
        isRefreshing = screenState.isLoading,
        onRefresh = {
            onEvent(CommentEvents.LoadComments)
        }
    ) {
        if (screenState.isLoading) {
            ShimmerList()
        } else {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (screenState.commentList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            AluminiumLottieAnimation(
                                lottieAnimation = R.raw.empty_box_animation,
                                iteration = 1,
                                modifier = Modifier
                                    .size(150.dp)
                            )
                            Text(text = "No comments")
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    ) {
                        items(screenState.commentList) { comment ->
                            if (!userMap.containsKey(comment.userId)) {
                                onEvent(CommentEvents.GetUser(comment.userId))
                            }
                            val userProfile = userMap[comment.userId]
                            ListItem(
                                leadingContent = {
                                    AluminiumAsyncImage(
                                        model = userProfile?.profileImage,
                                        contentDescription = "Profile Image",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape),
                                    )
                                },
                                headlineContent = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small)
                                    ) {
                                        userProfile?.let {
                                            Text(
                                                text = "${it.firstName} ${it.lastName}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = MaterialTheme.textSize.medium,
                                            )
                                        }
                                        Text(
                                            text = PrettyTimeUtils.getPrettyTime(comment.timestamp),
                                            fontSize = MaterialTheme.textSize.small
                                        )
                                    }
                                },
                                supportingContent = {
                                    AluminiumDescriptionText(
                                        description = comment.message,
                                    )
                                },
                                trailingContent = {
                                    CommentMenu(
                                        comment = comment,
                                        onProfileClick = {
                                            openScreen(Screen.ProfileScreen(comment.userId))
                                        },
                                        onDeleteClick = {
                                            onEvent(CommentEvents.ShowCommentDeleteDialog(comment.id))
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
                AluminiumTextField(
                    value = screenState.comment.message,
                    placeholder = "comment message...",
                    onValueChange = {
                        onEvent(CommentEvents.OnCommentChanged(it))
                    },
                    enable = !screenState.isCommentAdding,
                    supportingText = screenState.commentError?.message?.asString(),
                    trailingIcon = {
                        AluminiumLoadingIconButton(
                            icon = Icons.AutoMirrored.Filled.Send,
                            circularProgressIndicatorState = screenState.isCommentAdding,
                            onClick = {
                                onEvent(CommentEvents.OnAddCommentClick)
                            },
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.padding.small)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
private fun CommentMenu(
    comment: Comment,
    onProfileClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
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
                enabled = comment.userId == currentUserId,
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
}

@Composable
private fun CommentDeleteDialog(
    isDeleting: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.WarningAmber, contentDescription = "warning")
        },
        title = {
            Text(text = stringResource(R.string.comment_delete))
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
private fun ShimmerList() {
    Column {
        for (i in 1..10) {
            CommentPreviewShimmer()
        }
    }
}

@Composable
private fun CommentPreviewShimmer() {
    ListItem(
        leadingContent = {
            ShimmerBackground(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(16.dp)
            )
        },
        headlineContent = {
            ShimmerBackground(
                modifier = Modifier
                    .height(16.dp)
                    .width(MaterialTheme.Size.medium)
            )
        },
        supportingContent = {
            ShimmerBackground(
                modifier = Modifier
                    .height(16.dp)
                    .padding(top = MaterialTheme.padding.small)
                    .width(MaterialTheme.Size.large)
            )
        },
        trailingContent = {
            ShimmerBackground(
                modifier = Modifier
                    .size(16.dp)
            )
        },
        modifier = Modifier.shimmer()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun CommentScreenPreview() {
    CommentScreenContent(
        screenState = CommentState(),
        userMap = emptyMap(),
        onEvent = {},
        openScreen = {}
    )
}