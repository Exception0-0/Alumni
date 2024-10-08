package dev.than0s.aluminium.features.post.presentation.screens.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.core.composable.ProfileImageModifier
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CommentScreen(viewModel: CommentsViewModel = hiltViewModel()) {
    CommentScreenContent(
        commentList = viewModel.commentsList,
        currentComment = viewModel.currentComment,
        currentUserId = currentUserId!!,
        getUserProfile = viewModel::getUserProfile,
        onCurrentCommentChange = viewModel::onCurrentCommentChange,
        onAddCommentClick = viewModel::onAddCommentClick,
        onRemoveCommentClick = viewModel::onRemoveCommentClick
    )
}


@Composable
private fun CommentScreenContent(
    commentList: List<Comment>,
    currentComment: String,
    currentUserId: String,
    getUserProfile: (String) -> Flow<User>,
    onCurrentCommentChange: (String) -> Unit,
    onAddCommentClick: (() -> Unit) -> Unit,
    onRemoveCommentClick: (Comment) -> Unit
) {
    var circularProgressIndicatorState by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                AluminiumTextField(
                    value = currentComment,
                    placeholder = "Comment",
                    onValueChange = onCurrentCommentChange,
                    trailingIcon = {
                        AluminiumLoadingIconButton(
                            icon = Icons.AutoMirrored.Rounded.Send,
                            circularProgressIndicatorState = circularProgressIndicatorState,
                            onClick = {
                                circularProgressIndicatorState = true
                                onAddCommentClick {
                                    circularProgressIndicatorState = false
                                }
                            },
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding)
        ) {
            items(commentList) { comment ->
                CommentPreview(
                    comment = comment,
                    isCurrentUser = currentUserId == comment.userId,
                    getUserProfile = getUserProfile,
                    onRemoveCommentClick = {
                        onRemoveCommentClick(comment)
                    }
                )
            }
        }

    }
}

@Composable
private fun CommentPreview(
    comment: Comment,
    isCurrentUser: Boolean,
    getUserProfile: (String) -> Flow<User>,
    onRemoveCommentClick: () -> Unit
) {

    val userProfile = getUserProfile(comment.userId).collectAsState(User()).value

    AluminiumElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
            ) {
                AluminiumAsyncImage(
                    model = userProfile.profileImage,
                    settings = AluminiumAsyncImageSettings.UserProfile,
                    modifier = ProfileImageModifier.small
                )

                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    AluminiumTitleText(
                        title = "${userProfile.firstName} ${userProfile.lastName}",
                        fontSize = MaterialTheme.textSize.small
                    )
                    AluminiumDescriptionText(
                        description = comment.message,
                    )
                }
            }
            if (isCurrentUser) {
                CommentMenu(
                    onRemoveCommentClick = onRemoveCommentClick
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

@Preview(showSystemUi = true)
@Composable
private fun CommentScreenPreview() {
    CommentScreenContent(
        listOf(
            Comment(
                id = "",
                message = "Hey what, don't know any thing about me",
            ),
            Comment(
                id = "",
                message = "Hey what, don't know any thing about me",
            ),
            Comment(
                id = "",
                message = "Hey what, don't know any thing about me",
            )
        ),
        "",
        "",
        { emptyFlow() }, {}, {}, {}
    )
}