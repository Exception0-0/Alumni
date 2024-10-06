package dev.than0s.aluminium.features.post.presentation.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.composable.LoadingIconButton
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.ui.elevation
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun PostsScreen(
    viewModel: PostsScreenViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    PostsScreenContent(
        postsList = viewModel.postsList,
        onLikeClick = viewModel::onLikeClick,
        getUserProfile = viewModel::getUserProfile,
        openScreen = openScreen
    )
}

@Composable
private fun PostsScreenContent(
    postsList: List<Post>,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    getUserProfile: (String) -> Flow<User>,
    openScreen: (Screen) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(MaterialTheme.spacing.small)
    ) {
        items(postsList) { post ->
            PostItem(
                post = post,
                onLikeClick = onLikeClick,
                getUserProfile = getUserProfile,
                openCommentScreen = {
                    openScreen(Screen.CommentsScreen(post.id))
                }
            )
        }
    }
}


@Composable
fun PostItem(
    post: Post,
    openCommentScreen: () -> Unit,
    getUserProfile: (String) -> Flow<User>,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
) {
    val userProfile = getUserProfile(post.userId).collectAsState(initial = User()).value

    ElevatedCard(
        elevation = CardDefaults.cardElevation(MaterialTheme.elevation.medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .align(Alignment.CenterHorizontally)
                .width(360.dp)
        ) {

            UserDetail(
                user = userProfile
            )

            Text(
                text = post.title,
                fontWeight = FontWeight.W400,
                fontSize = MaterialTheme.textSize.gigantic
            )

            AsyncImage(
                model = post.file,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "post image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            PostStatus(
                isLiked = post.isLiked,
                onLikeClick = { hasLike, callback ->
                    onLikeClick(post.id, hasLike, callback)
                },
                openCommentScreen = openCommentScreen
            )
            Text(text = post.description)

        }
    }
}

@Composable
private fun UserDetail(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = user.profileImage,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "User profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = "${user.firstName} ${user.lastName}",
            fontWeight = FontWeight.W100,
            fontSize = MaterialTheme.textSize.large
        )
    }
}

@Composable
private fun PostStatus(
    isLiked: Boolean,
    onLikeClick: (Boolean, () -> Unit) -> Unit,
    openCommentScreen: () -> Unit,
) {
    var likeButtonState by rememberSaveable { mutableStateOf(isLiked) }
    var loadingLikeButtonState by rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            LoadingIconButton(
                icon = if (likeButtonState) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                circularProgressIndicatorState = loadingLikeButtonState,
                onClick = {
                    loadingLikeButtonState = true
                    onLikeClick(likeButtonState) {
                        loadingLikeButtonState = false
                        likeButtonState = !likeButtonState
                    }
                },
            )
            Text(text = "Like")
        }

        IconButton(onClick = openCommentScreen) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Comment,
                    contentDescription = "comment button",
                )
                Text(text = "Comment")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PostsScreenPreview() {
    PostsScreenContent(
        postsList = listOf(
            Post(
                id = "",
                title = "Than0s",
                description = "hello I'm Than0s don't talk to me",
            )
        ),
        onLikeClick = { _, _, _ -> },
        getUserProfile = { emptyFlow() },
        openScreen = {}
    )
}