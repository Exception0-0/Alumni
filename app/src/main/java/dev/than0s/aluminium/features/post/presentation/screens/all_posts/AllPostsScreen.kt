package dev.than0s.aluminium.features.post.presentation.screens.all_posts

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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.ui.elevation
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun AllPostsScreen(
    viewModel: AllPostsScreenViewModel = hiltViewModel()
) {
    val postsList = viewModel.postsFlow.collectAsState(initial = emptyList()).value
    AllPostsScreenContent(
        postsList = postsList,
        getUser = viewModel::getUser
    )
}

@Composable
private fun AllPostsScreenContent(
    postsList: List<Post>,
    getUser: (String) -> Flow<User>
) {
    LazyColumn {
        items(postsList) { post ->
//            val user = getUser(post.userId).collectAsState(User()).value
            PostItem(
                post = post,
                user = User()
            )
        }
    }
}


@Composable
private fun PostItem(post: Post, user: User) {
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
                user = user
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
        ),
        { emptyFlow() }
    )
}