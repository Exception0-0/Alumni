package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.composable.PostImageModifier
import dev.than0s.aluminium.features.post.domain.data_class.Post
import dev.than0s.aluminium.ui.spacing

@Composable
fun PostUploadScreen(viewModel: PostUploadViewModel = hiltViewModel(), popScreen: () -> Unit) {
    PostUploadScreenContent(
        post = viewModel.post,
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
    onFileUriChange: (Uri) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUploadClick: (() -> Unit) -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onFileUriChange(it)
            }
        }

    var circularProgressIndicatorState by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        AluminiumElevatedCard(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                AluminiumAsyncImage(
                    model = post.file,
                    settings = AluminiumAsyncImageSettings.PostImage,
                    modifier = PostImageModifier
                        .default
                        .clickable {
                            launcher.launch("image/*")
                        }
                )

                AluminiumTextField(
                    value = post.title,
                    placeholder = "Title",
                    onValueChange = {
                        onTitleChange(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                AluminiumTextField(
                    value = post.description,
                    placeholder = "Description",
                    onValueChange = {
                        onDescriptionChange(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                AluminiumLoadingElevatedButton(
                    label = "Upload",
                    circularProgressIndicatorState = circularProgressIndicatorState,
                    onClick = {
                        circularProgressIndicatorState = true
                        onUploadClick { circularProgressIndicatorState = false }
                    },
                    modifier = Modifier.align(CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PostUploadScreenPreview() {
    PostUploadScreenContent(
        Post(
            id = "",
            title = "Title",
            description = "asdfklajsdfkl jkldjfklj adklsj fkjasdklfjasdkj f klasdjfljdfjasdlfjasdjfkldajf kladj",
        ),
        {}, {}, {}, {},
    )
}