package dev.than0s.aluminium.features.post.presentation.screens.comments

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.post.domain.data_class.Comment
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun CommentScreen(viewModel: CommentsViewModel = hiltViewModel()) {
    val commentList = viewModel.commentFlow.collectAsState(emptyList()).value
    CommentScreenContent(
        commentList = commentList,
        currentComment = viewModel.currentComment,
        onCurrentCommentChange = viewModel::onCurrentCommentChange,
        onAddCommentClick = viewModel::onAddCommentClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommentScreenContent(
    commentList: List<Comment>,
    currentComment: String,
    onCurrentCommentChange: (String) -> Unit,
    onAddCommentClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Comments")
                }
            )
        },
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                OutlinedTextField(
                    value = currentComment,
                    placeholder = {
                        Text(text = "Comment")
                    },
                    onValueChange = onCurrentCommentChange,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Send,
                            contentDescription = "send comment",
                            modifier = Modifier
                                .clickable {
                                    onAddCommentClick()
                                }
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
                CommentPreview(comment)
            }
        }

    }
}

@Composable
private fun CommentPreview(comment: Comment) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        ) {
            AsyncImage(
                model = comment.user.profileImage,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "User profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.dp)
            )

            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Text(
                    text = "${comment.user.firstName} ${comment.user.lastName}",
                    fontWeight = FontWeight.W100,
                    fontSize = MaterialTheme.textSize.small
                )
                Text(
                    text = comment.message,
                    fontWeight = FontWeight.W300
                )
            }
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
                user = User(
                    firstName = "Than0s",
                    userId = ""
                )
            ),
            Comment(
                id = "",
                message = "Hey what, don't know any thing about me",
                user = User(
                    firstName = "Than0s",
                    userId = ""
                )
            ),
            Comment(
                id = "",
                message = "Hey what, don't know any thing about me",
                user = User(
                    firstName = "Than0s",
                    userId = ""
                )
            )
        ),
        "",
        {}, {}
    )
}