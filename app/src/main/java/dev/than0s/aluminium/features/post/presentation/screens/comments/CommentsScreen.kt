package dev.than0s.aluminium.features.post.presentation.screens.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.Comment
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.lottie_animation.AnimationNoData
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredIconButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredOutlinedTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWarningDialog
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerListItem
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
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
        PreferredWarningDialog(
            title = stringResource(R.string.comment_delete),
            description = stringResource(R.string.alert_message),
            isLoading = screenState.isDeleting,
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
            if (screenState.commentList.isEmpty()) {
                AnimationNoData(title = "No comments")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(screenState.commentList) { comment ->
                        if (!userMap.containsKey(comment.userId)) {
                            onEvent(CommentEvents.GetUser(comment.userId))
                        }
                        val userProfile = userMap[comment.userId]
                        ListItem(
                            leadingContent = {
                                PreferredAsyncImage(
                                    model = userProfile?.profileImage,
                                    shape = CircleShape,
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            },
                            headlineContent = {
                                PreferredRow(
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
                                Text(
                                    text = comment.message,
                                    fontSize = MaterialTheme.textSize.medium
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
            PreferredOutlinedTextField(
                value = screenState.comment.message,
                placeholder = "comment message...",
                onValueChange = {
                    onEvent(CommentEvents.OnCommentChanged(it))
                },
                enabled = !screenState.isCommentAdding,
                singleLine = false,
                supportingText = screenState.commentError?.message?.asString(),
                trailingIcon = {
                    PreferredIconButton(
                        icon = Icons.AutoMirrored.Filled.Send,
                        isLoading = screenState.isCommentAdding,
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
private fun ShimmerList() {
    PreferredColumn {
        for (i in 1..10) {
            ShimmerListItem()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CommentScreenPreview() {
//    CommentScreenContent(
//        screenState = CommentState(),
//        userMap = emptyMap(),
//        onEvent = {},
//        openScreen = {}
//    )
}