package dev.than0s.aluminium.features.post.presentation.screens.my_posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.composable.WarningDialog
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.ui.elevation
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun MyPostsScreen(viewModel: MyPostViewModel = hiltViewModel(), openScreen: (String) -> Unit) {
    val postsList = viewModel.postFlow.collectAsState(initial = emptyList())
    MyPostScreenContent(
        postsList = postsList.value,
        openScreen = openScreen,
        onPostDeleteClick = viewModel::onPostDeleteClick
    )
}

@Composable
private fun MyPostScreenContent(
    postsList: List<Post>,
    openScreen: (String) -> Unit,
    onPostDeleteClick: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    openScreen(Screen.PostUploadScreen.route)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "post add")
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                Text(text = "Add Post")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(postsList) {
                PostItem(
                    post = it,
                    onPostDeleteClick = onPostDeleteClick,
                )
            }
        }
    }
}

@Composable
private fun PostItem(post: Post, onPostDeleteClick: (String) -> Unit) {
    var warningState by rememberSaveable { mutableStateOf(false) }
    if (warningState) {
        WarningDialog(
            title = "Post Delete",
            text = "Are you sure you want to delete this post?",
            onDismissRequest = {
                warningState = false
            },
            onConfirmation = {
                onPostDeleteClick(post.id)
                warningState = false
            }
        )
    }

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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.W400,
                    fontSize = MaterialTheme.textSize.gigantic
                )
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "delete post",
                    modifier = Modifier.clickable {
                        warningState = true
                    }
                )
            }
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

@Preview(showSystemUi = true)
@Composable
private fun MyPostScreenPreview() {
    MyPostScreenContent(
        listOf(
            Post(
                id = "",
                user = User(userId = ""),
                title = "Title",
                description = "asdfklajsdfkl jkldjfklj adklsj fkjasdklfjasdkj f klasdjfljdfjasdlfjasdjfkldajf kladj",
                timestamp = Timestamp.now()
            ),
        ),
        {},
        {}
    )
}