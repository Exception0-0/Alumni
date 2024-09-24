package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.composable.LoadingButton
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.features.post.domain.data_class.User
import dev.than0s.mydiary.ui.spacing

@Composable
fun PostUploadScreen(viewModel: PostUploadViewModel = hiltViewModel(), popScreen: () -> Unit) {
    PostUploadScreenContent(
        post = viewModel.post,
        circularProgressIndicatorState = viewModel.circularProgressIndicatorState,
        onFileUriChange = viewModel::onFileUriChange,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onUploadClick = {
            viewModel.onUploadClick(popScreen)
        }
    )
}

@Composable
private fun PostUploadScreenContent(
    post: Post,
    circularProgressIndicatorState: Boolean,
    onFileUriChange: (Uri) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUploadClick: () -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onFileUriChange(it)
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(MaterialTheme.spacing.large)
    ) {
        AsyncImage(
            model = post.file,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "post's image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(250.dp)
                .width(350.dp)
                .clickable {
                    launcher.launch("image/*")
                }
        )
        TextField(
            value = post.title,
            label = {
                Text("Title")
            },
            onValueChange = {
                onTitleChange(it)
            }
        )

        TextField(
            value = post.description,
            label = {
                Text("Description")
            },
            onValueChange = {
                onDescriptionChange(it)
            }
        )

        LoadingButton(
            label = "Upload",
            circularProgressIndicatorState = circularProgressIndicatorState,
            onClick = onUploadClick,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PostUploadScreenPreview() {
    PostUploadScreenContent(Post(
        id = "",
        user = User(userId = ""),
        title = "Title",
        description = "asdfklajsdfkl jkldjfklj adklsj fkjasdklfjasdkj f klasdjfljdfjasdlfjasdjfkldajf kladj",
    ), false, {}, {}, {}, {})
}