package dev.than0s.aluminium.features.profile.presentation.screens.post

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.data_class.Like
import dev.than0s.aluminium.core.domain.data_class.Post
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWrappedText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerBackground
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.postHeight
import dev.than0s.aluminium.ui.roundedCorners
import dev.than0s.aluminium.ui.textSize

@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    PostsContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun PostsContent(
    screenState: PostsState,
    onEvent: (PostsEvents) -> Unit,
) {
    if (screenState.isLoading) {
        ShimmerEffectLoading(
            modifier = Modifier.height(350.dp)
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .height(350.dp),
            columns = GridCells.Adaptive(MaterialTheme.postHeight.small),
            content = {
                items(screenState.postList) {
                    PostImagePreview(
                        post = it
                    )
                }
            }
        )
    }
}

@Composable
private fun PostImagePreview(post: Post) {
    Box(
        modifier = Modifier
            .size(MaterialTheme.postHeight.small)
            .padding(MaterialTheme.padding.verySmall)
    ) {
        PreferredAsyncImage(
            model = post.files[0],
            contentDescription = "post image",
            shape = RoundedCornerShape(roundedCorners.extraSmall),
        )
        if (post.files.size > 1) {
            Image(
                painterResource(R.drawable.stacks_icon),
                contentDescription = "",
                modifier = Modifier
                    .padding(MaterialTheme.padding.verySmall)
                    .align(Alignment.TopEnd)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(roundedCorners.small)
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostImageDetail(
    post: Post,
    likeStatus: Like?,
    onDeleteClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onPostImageClick: (Uri) -> Unit,
) {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
    ) {
        val configuration = LocalConfiguration.current
        var expanded by remember { mutableStateOf(false) }
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { post.files.size },
            preferredItemWidth = configuration.screenWidthDp.dp,
        ) { index ->
            val uri = post.files[index]
            PreferredAsyncImage(
                model = uri,
                contentDescription = "post image",
                modifier = Modifier
                    .height(MaterialTheme.postHeight.default)
                    .clickable {
                        onPostImageClick(uri)
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PreferredRow(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
            ) {
                IconButton(
                    content = {
                        Icon(
                            imageVector = if (likeStatus != null) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "like button"
                        )
                    },
                    onClick = onLikeClick,
                )

                IconButton(
                    onClick = onCommentClick,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Comment,
                            contentDescription = "comment button",
                        )
                    }
                )
            }

            PreferredRow(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                IconButton(
                    content = {
                        Icon(
                            imageVector = if (likeStatus != null) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "save button"
                        )
                    },
                    onClick = {},
                )
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
                                Text("Delete")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            },
                            enabled = post.userId == currentUserId,
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
        }

        PreferredWrappedText(
            text = post.caption,
            fontSize = MaterialTheme.textSize.medium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ShimmerEffectLoading(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MaterialTheme.postHeight.small),
        modifier = modifier
    ) {
        items(8) {
            PostShimmerCard()
        }
    }
}

@Composable
private fun PostShimmerCard() {
    ShimmerBackground(
        shape = RoundedCornerShape(roundedCorners.extraSmall),
        modifier = Modifier
            .shimmer()
            .size(MaterialTheme.postHeight.small)
            .padding(MaterialTheme.padding.verySmall)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PostsContent(
        screenState = PostsState(),
        onEvent = {}
    )
}