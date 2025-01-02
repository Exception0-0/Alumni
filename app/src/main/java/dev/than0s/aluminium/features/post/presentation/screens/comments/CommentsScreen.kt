package dev.than0s.aluminium.features.post.presentation.screens.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import dev.than0s.aluminium.core.domain.data_class.Comment
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminumCircularLoading
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.spacing
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

@Composable
private fun CommentScreenContent(
    screenState: CommentState,
    userMap: Map<String, User>,
    onEvent: (CommentEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    if (screenState.isLoading) {
        AluminumCircularLoading()
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                            ) {
                                Text(
                                    text = "${userProfile?.firstName} ${userProfile?.lastName}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.textSize.medium,
                                )
                                Text(
                                    text = PrettyTimeUtils.getPrettyTime(comment.timestamp),
                                    fontSize = MaterialTheme.textSize.extraSmall
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

                                }
                            )
                        }
                    )
                }
            }
            AluminiumTextField(
                value = screenState.comment.message,
                placeholder = "comment message...",
                onValueChange = {
                    onEvent(CommentEvents.OnCommentChanged(it))
                },
                supportingText = screenState.commentError?.message?.asString(),
                trailingIcon = {
                    AluminiumLoadingIconButton(
                        icon = Icons.AutoMirrored.Rounded.Send,
                        circularProgressIndicatorState = screenState.isCommentAdding,
                        onClick = {
                            onEvent(CommentEvents.OnAddCommentClick)
                        },
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small)
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