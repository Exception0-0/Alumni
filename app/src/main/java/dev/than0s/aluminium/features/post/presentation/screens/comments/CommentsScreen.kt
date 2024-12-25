package dev.than0s.aluminium.features.post.presentation.screens.comments

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.composable.ProfileImageModifier
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminumLoading
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.ui.Size
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
        onEvents = viewModel::onEvent,
        openScreen = openScreen
    )
}


@Composable
private fun CommentScreenContent(
    screenState: CommentState,
    userMap: Map<String, User>,
    onEvents: (CommentEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    if (screenState.isLoading) {
        AluminumLoading()
    } else {
        Scaffold(
            bottomBar = {
                Surface(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                ) {
                    AluminiumTextField(
                        value = screenState.comment.message,
                        placeholder = "Comment",
                        onValueChange = {
                            onEvents(CommentEvents.OnCommentChanged(it))
                        },
                        supportingText = screenState.commentError?.message?.asString(),
                        trailingIcon = {
                            AluminiumLoadingIconButton(
                                icon = Icons.AutoMirrored.Rounded.Send,
                                circularProgressIndicatorState = screenState.isCommentAdding,
                                onClick = {
                                    onEvents(CommentEvents.OnAddCommentClick)
                                },
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
        ) { contentPadding ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier.padding(contentPadding)
            ) {
                items(screenState.commentList) { comment ->
                    CommentPreview(
                        comment = comment,
                        userMap = userMap,
                        onEvent = onEvents,
                        onProfileClick = {
                            openScreen(Screen.ProfileScreen(userId = comment.userId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentPreview(
    comment: Comment,
    userMap: Map<String, User>,
    onEvent: (CommentEvents) -> Unit,
    onProfileClick: () -> Unit
) {
    onEvent(CommentEvents.GetUser(comment.userId))
    val userProfile = userMap[comment.userId]

    AluminiumElevatedCard(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.small)
            .fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .clickable {
                        onProfileClick()
                    }
            ) {
                AluminiumAsyncImage(
                    model = userProfile?.profileImage,
                    settings = AluminiumAsyncImageSettings.UserProfile,
                    modifier = ProfileImageModifier.small
                )

                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    AluminiumTitleText(
                        title = "${userProfile?.firstName} ${userProfile?.lastName}",
                        fontSize = MaterialTheme.textSize.small
                    )
                    AluminiumDescriptionText(
                        description = comment.message,
                    )
                }
            }
            if (comment.id == currentUserId) {
                CommentMenu(
                    onRemoveCommentClick = {
                        onEvent(CommentEvents.OnCommentRemovedClick(comment))
                    }
                )
            }
        }
    }
}

@Composable
private fun CommentMenu(
    onRemoveCommentClick: () -> Unit
) {
    var dropDownMenuState by rememberSaveable { mutableStateOf(false) }
    Column {
        IconButton(
            onClick = {
                dropDownMenuState = !dropDownMenuState
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "menu",
                )
            }
        )
        DropdownMenu(
            expanded = dropDownMenuState,
            onDismissRequest = {
                dropDownMenuState = false
            }
        ) {
            DropdownMenuItem(
                text = {
                    Text("Delete")
                },
                onClick = {
                    onRemoveCommentClick()
                    dropDownMenuState = false
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "comment delete"
                    )
                }
            )
        }
    }
}

//@Composable
//private fun ShimmerCommentCard() {
//    AluminiumElevatedCard(
//        modifier = Modifier
//            .padding(horizontal = MaterialTheme.spacing.small)
//            .fillMaxWidth()
//            .shimmer()
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(MaterialTheme.spacing.small)
//        ) {
//            Row(
//                verticalAlignment = Alignment.Top,
//            ) {
//                ShimmerBackground(
//                    modifier = ProfileImageModifier.small
//                )
//                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
//                ) {
//                    ShimmerBackground(
//                        modifier = Modifier
//                            .height(MaterialTheme.textSize.small.value.dp)
//                            .width(MaterialTheme.Size.small)
//                    )
//                    ShimmerBackground(
//                        modifier = Modifier
//                            .height(MaterialTheme.textSize.small.value.dp)
//                            .width(MaterialTheme.Size.medium)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun ShimmerCommentList() {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//    ) {
//        for (i in 1..10) {
//            ShimmerCommentCard()
//        }
//    }
//}

@Preview(showSystemUi = true)
@Composable
private fun CommentScreenPreview() {
    CommentScreenContent(
        screenState = CommentState(),
        userMap = emptyMap(),
        onEvents = {},
        openScreen = {}
    )
}