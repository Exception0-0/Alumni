package dev.than0s.aluminium.features.post.presentation.screens.post_upload

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.POST_IMAGES_COUNT
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAddPicture
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredOutlinedTextField
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.postHeight
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PostUploadScreen(viewModel: PostUploadViewModel = hiltViewModel(), popScreen: () -> Unit) {
    PostUploadScreenContent(
        screenStates = viewModel.screenStatus,
        popScreen = popScreen,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostUploadScreenContent(
    screenStates: PostUploadScreenStatus,
    popScreen: () -> Unit,
    onEvent: (PostUploadScreenEvents) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val pickMultipleMedia =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(POST_IMAGES_COUNT)
        ) { images ->
            if (images.isNotEmpty()) {
                onEvent(PostUploadScreenEvents.OnImagesSelected(images))
            }
        }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredColumn(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
        ) {
            if (screenStates.post.files.isEmpty()) {
                PreferredAddPicture(
                    model = null,
                    shape = RoundedCornerShape(roundedCorners.small),
                    modifier = Modifier
                        .height(MaterialTheme.postHeight.default)
                        .fillMaxWidth(),
                    onAddPicture = {
                        pickMultipleMedia.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            } else {
                HorizontalMultiBrowseCarousel(
                    state = CarouselState { screenStates.post.files.size },
                    preferredItemWidth = configuration.screenWidthDp.dp,
                    modifier = Modifier
                        .height(MaterialTheme.postHeight.default)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickable(enabled = !screenStates.isLoading) {
                            pickMultipleMedia.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                ) { index ->
                    val uri = screenStates.post.files[index]
                    PreferredAsyncImage(
                        model = uri,
                        contentDescription = "selected image",
                        modifier = Modifier
                            .height(MaterialTheme.postHeight.default)
                    )
                }
            }
            PreferredOutlinedTextField(
                value = screenStates.post.caption,
                onValueChange = {
                    onEvent(PostUploadScreenEvents.OnCaptionChanged(it))
                },
                placeholder = "caption",
                enabled = !screenStates.isLoading,
                supportingText = screenStates.titleError?.message?.asString(),
                singleLine = false,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.padding.extraSmall)
                    .fillMaxWidth()
            )
            PreferredFilledButton(
                onClick = {
                    onEvent(
                        PostUploadScreenEvents.OnUploadClick(
                            popScreen = popScreen
                        )
                    )
                },
                enabled = !screenStates.isLoading,
                isLoading = screenStates.isLoading,
                content = {
                    Text(text = "Post")
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PostUploadScreenPreview() {
    PostUploadScreenContent(
        screenStates = PostUploadScreenStatus(),
        popScreen = {},
        onEvent = {}
    )
}