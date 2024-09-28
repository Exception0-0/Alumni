package dev.than0s.aluminium.features.post.presentation.screens.all_posts

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
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.ui.elevation
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun AllPostsScreen(
    viewModel: AllPostsScreenViewModel = hiltViewModel(),
    openScreen: (String) -> Unit
) {
    val postsList = viewModel.postsFlow.collectAsState(initial = emptyList()).value
    AllPostsScreenContent(
        postsList = postsList,
        onLikeClick = viewModel::onLikeClick,
        openScreen = openScreen
    )
}

@Composable
private fun AllPostsScreenContent(
    postsList: List<Post>,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    openScreen: (String) -> Unit
) {
    LazyColumn {
        items(postsList) { post ->
            PostItem(
                post = post,
                onLikeClick = onLikeClick,
                openCommentScreen = {
                    openScreen("${Screen.CommentsScreen.route}/${post.id}")
                }
            )
        }
    }
}


@Composable
private fun PostItem(
    post: Post,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    openCommentScreen: () -> Unit
) {
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
                user = post.user
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
                hasLike = post.hasLiked,
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
fun UserDetail(user: User) {
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
    hasLike: Boolean,
    onLikeClick: (Boolean, () -> Unit) -> Unit,
    openCommentScreen: () -> Unit,
) {
    var likeButtonState by rememberSaveable { mutableStateOf(hasLike) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {
        Icon(
            imageVector = if (likeButtonState) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
            contentDescription = "like button",
            modifier = Modifier.clickable {
                onLikeClick(likeButtonState) {
                    likeButtonState = !likeButtonState
                }
            }
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
        Text(
            text = "Like",
        )

        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))

        Icon(
            painter = painterResource(R.drawable.outline_comment_24),
            contentDescription = "comment button",
            modifier = Modifier.clickable {
                openCommentScreen()
            }
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
        Text(
            text = "Comment",
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AllPostsScreenPreview() {
    AllPostsScreenContent(
        postsList = listOf(
            Post(
                id = "",
                user = User(userId = ""),
                title = "Than0s",
                description = "hello I'm Than0s don't talk to me",
            )
        ),
        onLikeClick = { _, _, _ -> },
        openScreen = {}
    )
}