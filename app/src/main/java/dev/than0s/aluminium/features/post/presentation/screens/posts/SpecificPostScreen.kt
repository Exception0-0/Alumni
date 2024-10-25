package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.composable.AluminiumAlertDialog
import dev.than0s.aluminium.core.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.ui.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SpecificPostsScreen(
    viewModel: PostsScreenViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    SpecificPostsScreenContent(
        postsList = viewModel.postsList,
        isCurrentUser = viewModel.postScreenArgs.userId == currentUserId,
        onPostDeleteClick = viewModel::onPostDeleteClick,
        onLikeClick = viewModel::onLikeClick,
        getUserProfile = viewModel::getUserProfile,
        openScreen = openScreen
    )
}

@Composable
private fun SpecificPostsScreenContent(
    postsList: List<Post>,
    isCurrentUser: Boolean,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    onPostDeleteClick: (String) -> Unit,
    getUserProfile: (String) -> Flow<User>,
    openScreen: (Screen) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(MaterialTheme.spacing.small)
    ) {
        items(postsList) { post ->
            SpecificPostItem(
                post = post,
                isCurrentUser = isCurrentUser,
                onLikeClick = onLikeClick,
                getUserProfile = getUserProfile,
                onPostDeleteClick = onPostDeleteClick,
                openProfileScreen = {
                    openScreen(Screen.ProfileScreen(post.userId))
                },
                openCommentScreen = {
                    openScreen(Screen.CommentsScreen(post.id))
                }
            )
        }
    }
}

@Composable
private fun SpecificPostItem(
    post: Post,
    isCurrentUser: Boolean,
    openProfileScreen: () -> Unit,
    openCommentScreen: () -> Unit,
    getUserProfile: (String) -> Flow<User>,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    onPostDeleteClick: (String) -> Unit,
) {
    var warningState by rememberSaveable { mutableStateOf(false) }

    if (warningState) {
        AluminiumAlertDialog(
            title = "Post Delete",
            description = "Are you sure you want to delete this post?",
            onDismissRequest = {
                warningState = false
            },
            onConfirmation = {
                onPostDeleteClick(post.id)
                warningState = false
            }
        )
    }

    PostItem(
        post = post,
        onLikeClick = onLikeClick,
        getUserProfile = getUserProfile,
        openProfileScreen = openProfileScreen,
        openCommentScreen = openCommentScreen
    )

    if (isCurrentUser) {
        AluminiumElevatedButton(
            label = "Delete Post",
            onClick = {
                warningState = true
            },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PostsScreenPreview() {
    SpecificPostsScreenContent(
        postsList = listOf(
            Post(
                id = "",
                title = "Than0s",
                description = "hello I'm Than0s don't talk to me",
            )
        ),
        isCurrentUser = true,
        onPostDeleteClick = {},
        onLikeClick = { _, _, _ -> },
        getUserProfile = { emptyFlow() },
        openScreen = {}
    )
}