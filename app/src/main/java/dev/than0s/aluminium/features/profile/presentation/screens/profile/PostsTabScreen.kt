package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.composable.AluminiumCard
import dev.than0s.aluminium.core.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumLoadingIconButton
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.core.composable.PostImageModifier
import dev.than0s.aluminium.core.composable.ProfileImageModifier
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.aluminium.features.post.presentation.screens.posts.PostItem
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize
import kotlinx.coroutines.flow.Flow

@Composable
fun PostsTabScreen(
    postsList: List<Post>,
    isPostsLoading: Boolean,
    onLikeClick: (String, Boolean, () -> Unit) -> Unit,
    openScreen: (Screen) -> Unit
) {
    var dialogState by rememberSaveable { mutableStateOf("-1") }

    if (dialogState != "-1") {
        Dialog(
            onDismissRequest = {
                dialogState = "-1"
            },
            content = {
                AluminiumElevatedCard {
                    PostDetailCard(
                        post = postsList.find { it.id == dialogState }!!,
                        onLikeClick = { isLiked, callback ->
                            onLikeClick(dialogState, isLiked, callback)
                        },
                        openCommentScreen = {
                            openScreen(Screen.CommentsScreen(dialogState))
                        },
                    )
                }
            }
        )
    }
    if (isPostsLoading) {
        ShimmerPostList()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(MaterialTheme.Size.default),
            content = {
                items(postsList) {
                    PostPreviewCard(
                        post = it,
                        onClick = { postId ->
                            dialogState = postId
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun PostPreviewCard(
    post: Post,
    onClick: (String) -> Unit
) {
    AluminiumCard(
        onClick = {
            onClick(post.id)
        },
        modifier = Modifier
            .size(
                MaterialTheme.Size.medium
            )
            .padding(MaterialTheme.spacing.extraSmall)
    ) {
        AluminiumAsyncImage(
            model = post.file,
            settings = AluminiumAsyncImageSettings.PostImage,
            modifier = Modifier
        )
    }
}

@Composable
fun PostDetailCard(
    post: Post,
    openCommentScreen: () -> Unit,
    onLikeClick: (Boolean, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
            .padding(MaterialTheme.spacing.medium)
            .width(360.dp)
    ) {

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
                    onLikeClick(hasLike, callback)
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
private fun ShimmerPostCard() {
    AluminiumCard(
        modifier = Modifier
            .size(MaterialTheme.Size.medium)
            .padding(MaterialTheme.spacing.extraSmall)
            .shimmer(),
        content = {}
    )
}

@Composable
private fun ShimmerPostList() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MaterialTheme.Size.default),
        content = {
            items(10) {
                ShimmerPostCard()
            }
        }
    )
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
private fun Preview() {
    PostsTabScreen(
        postsList = listOf(
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY),
            Post(file = Uri.EMPTY)
        ),
        isPostsLoading = false,
        onLikeClick = { _, _, _ ->

        },
        openScreen = {}
    )
}