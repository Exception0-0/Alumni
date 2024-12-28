package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.ui.spacing

@Composable
fun PostUploadScreen(viewModel: PostUploadViewModel = hiltViewModel(), popScreen: () -> Unit) {
    PostUploadScreenContent(
        screenStates = viewModel.screenStatus,
        popScreen = popScreen,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun PostUploadScreenContent(
    screenStates: PostStatus,
    popScreen: () -> Unit,
    onEvent: (PostEvents) -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onEvent(PostEvents.OnFileUriChanged(it))
            }
        }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        AluminiumElevatedCard(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .verticalScroll(rememberScrollState())
            ) {
                AluminiumTitleText(
                    title = "Post Upload"
                )
                AluminiumAsyncImage(
                    model = screenStates.post.file,
                    modifier = Modifier
                        .height(450.dp)
                        .width(360.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )

                AluminiumTextField(
                    value = screenStates.post.title,
                    placeholder = "Title",
                    supportingText = screenStates.titleError?.message?.asString(),
                    onValueChange = {
                        onEvent(PostEvents.OnTitleChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                AluminiumTextField(
                    value = screenStates.post.description,
                    placeholder = "Description",
                    supportingText = screenStates.descriptionError?.message?.asString(),
                    onValueChange = {
                        onEvent(PostEvents.OnDescriptionChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                AluminiumLoadingElevatedButton(
                    label = "Upload",
                    circularProgressIndicatorState = screenStates.isLoading,
                    onClick = {
                        onEvent(PostEvents.OnUploadClick(popScreen = popScreen))
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
        screenStates = PostStatus(),
        popScreen = {},
        onEvent = {}
    )
}