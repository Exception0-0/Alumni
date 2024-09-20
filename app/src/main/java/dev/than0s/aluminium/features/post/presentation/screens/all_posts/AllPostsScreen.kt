package dev.than0s.aluminium.features.post.presentation.screens.all_posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.composable.WarningDialog
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.mydiary.ui.elevation
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun AllPostsScreen(
    viewModel: AllPostsScreenViewModel = hiltViewModel()
) {
    val postsList = viewModel.postsFlow.collectAsState(initial = emptyList()).value
    AllPostsScreenContent(
        postsList = postsList
    )
}

@Composable
private fun AllPostsScreenContent(
    postsList: List<Post>,
) {
    LazyColumn {
        items(postsList) {
            PostItem(post = it)
        }
    }
}


@Composable
private fun PostItem(post: Post) {
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
                userId = post.userId,
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

            Text(text = post.description)
        }
    }
}

@Composable
fun UserDetail(userId: String) {

}

@Preview(showSystemUi = true)
@Composable
private fun AllPostsScreenPreview() {
    AllPostsScreenContent(
        postsList = listOf(
            Post(
                userId = "",
                title = "Than0s",
                description = "hello I'm Than0s don't talk to me"
            )
        )
    )
}