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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import dev.than0s.aluminium.core.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.composable.AluminiumCard
import dev.than0s.aluminium.core.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.core.composable.PostImageModifier
import dev.than0s.aluminium.core.composable.ProfileImageModifier
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.ui.roundCorners
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
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier.padding(MaterialTheme.spacing.medium)
    ) {
        items(postsList) { post ->
            AluminiumElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PostItem(
                    post = post,
                    onLikeClick = onLikeClick,
                    getUserProfile = getUserProfile,
                    openProfileScreen = {
                        openScreen(Screen.ProfileScreen(post.userId))
                    },
                    openCommentScreen = {
                        openScreen(Screen.CommentsScreen(post.id))
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


@Composable
fun PostItem(
    post: Post,
    openProfileScreen: () -> Unit,
    openCommentScreen: () -> Unit,
    getUserProfile: (String) -> Flow<User>,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile = getUserProfile(post.userId).collectAsState(initial = User()).value
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .width(360.dp)
    ) {

        AluminiumCard(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(MaterialTheme.roundCorners.default))
                .clickable {
                    openProfileScreen()
                }
        ) {
            UserDetail(
                user = userProfile,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
            )
        }

        AluminiumTitleText(
            title = post.title,
        )

        AluminiumDescriptionText(
            description = post.description,
        )

        AluminiumAsyncImage(
            model = post.file,
            settings = AluminiumAsyncImageSettings.PostImage,
            modifier = PostImageModifier.default
        )

        AluminiumCard {
            PostStatus(
                isLiked = post.isLiked,
                onLikeClick = { hasLike, callback ->
                    onLikeClick(post.id, hasLike, callback)
                },
                openCommentScreen = openCommentScreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small)
            )
        }
    }
}

@Composable
private fun UserDetail(
    user: User,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AluminiumAsyncImage(
            model = user.profileImage,
            settings = AluminiumAsyncImageSettings.UserProfile,
            modifier = ProfileImageModifier.medium
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        AluminiumTitleText(
            title = "${user.firstName} ${user.lastName}",
            fontSize = MaterialTheme.textSize.large
        )
    }
}

@Composable
private fun PostStatus(
    isLiked: Boolean,
    onLikeClick: (Boolean, () -> Unit) -> Unit,
    openCommentScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    var likeButtonState by rememberSaveable { mutableStateOf(isLiked) }
    var loadingLikeButtonState by rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            AluminiumLoadingIconButton(
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
            AluminiumTitleText(
                title = "Like",
                fontSize = MaterialTheme.textSize.small
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            IconButton(onClick = openCommentScreen) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Comment,
                    contentDescription = "comment button",
                )
            }
            AluminiumTitleText(
                title = "Comments",
                fontSize = MaterialTheme.textSize.small
            )
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