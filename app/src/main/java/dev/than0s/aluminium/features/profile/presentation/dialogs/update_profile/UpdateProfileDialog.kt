package dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.data.remote.COVER_IMAGE
import dev.than0s.aluminium.core.data.remote.PROFILE_IMAGE
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun UpdateProfileDialog(
    viewModel: UpdateProfileDialogViewModel = hiltViewModel(),
    popScreen: () -> Unit,
) {
    UpdateProfileDialogContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        popScreen = popScreen
    )
}

@Composable
private fun UpdateProfileDialogContent(
    screenState: UpdateProfileDialogState,
    onEvent: (UpdateProfileDialogEvents) -> Unit,
    popScreen: () -> Unit
) {
    val imageSelectionState = rememberSaveable {
        mutableMapOf(
            COVER_IMAGE to false,
            PROFILE_IMAGE to false,
        )
    }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { imageUri ->
        imageUri?.let {
            imageSelectionState.let { map ->
                if (map[COVER_IMAGE]!!) {
                    onEvent(UpdateProfileDialogEvents.OnCoverImageChanged(it))
                    map[COVER_IMAGE] = false
                } else if (map[PROFILE_IMAGE]!!) {
                    onEvent(UpdateProfileDialogEvents.OnProfileImageChanged(it))
                    map[PROFILE_IMAGE] = false
                }
            }
        }
    }

    Surface(
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
    ) {
        if (screenState.isLoading) {
            LoadingShimmerEffect()
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Text(
                    text = "Profile",
                    fontSize = MaterialTheme.textSize.gigantic,
                    fontWeight = FontWeight.W900
                )
                AluminiumAsyncImage(
                    model = screenState.userProfile.coverImage,
                    contentDescription = "user cover image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .clickable(enabled = !screenState.isUpdating) {
                            imageSelectionState[COVER_IMAGE] = true
                            pickMedia.launch(
                                input = PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageAndVideo
                                ),
                            )
                        }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                ) {

                    AluminiumAsyncImage(
                        model = screenState.userProfile.profileImage,
                        contentDescription = "user profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable(enabled = !screenState.isUpdating) {
                                imageSelectionState[PROFILE_IMAGE] = true
                                pickMedia.launch(
                                    input = PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageAndVideo
                                    ),
                                )
                            }
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        AluminiumTextField(
                            value = screenState.userProfile.firstName,
                            onValueChange = {
                                onEvent(UpdateProfileDialogEvents.OnFirstNameChanged(it))
                            },
                            enable = !screenState.isUpdating,
                            supportingText = screenState.firstNameError?.message?.asString(),
                            placeholder = "First Name",
                        )
                        AluminiumTextField(
                            value = screenState.userProfile.lastName,
                            enable = !screenState.isUpdating,
                            onValueChange = {
                                onEvent(UpdateProfileDialogEvents.OnLastNameChanged(it))
                            },
                            supportingText = screenState.lastNameError?.message?.asString(),
                            placeholder = "Last Name"
                        )
                    }
                }
                AluminiumTextField(
                    value = screenState.userProfile.bio,
                    onValueChange = {
                        onEvent(UpdateProfileDialogEvents.OnBioChanged(it))
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.bioError?.message?.asString(),
                    placeholder = "Bio",
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = popScreen) {
                        Text(text = "Cancel")
                    }

                    AluminiumLoadingTextButton(
                        label = "Update",
                        circularProgressIndicatorState = screenState.isUpdating,
                        onClick = {
                            onEvent(
                                UpdateProfileDialogEvents.OnProfileUpdateClick(
                                    onSuccessful = popScreen
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .shimmer()
            .padding(MaterialTheme.spacing.medium)
    ) {
        ShimmerBackground(
            modifier = Modifier
                .height(MaterialTheme.Size.extraSmall)
                .width(MaterialTheme.Size.medium)
        )
        ShimmerBackground(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {

            ShimmerBackground(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(100.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                ShimmerBackground(
                    modifier = Modifier
                        .height(MaterialTheme.Size.small)
                        .width(MaterialTheme.Size.large)
                )
                ShimmerBackground(
                    modifier = Modifier
                        .height(MaterialTheme.Size.small)
                        .width(MaterialTheme.Size.large)
                )
            }
        }
        ShimmerBackground(
            modifier = Modifier
                .height(MaterialTheme.Size.small)
                .fillMaxSize()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateProfileDialogPreview() {
    UpdateProfileDialogContent(
        screenState = UpdateProfileDialogState(),
        onEvent = {},
        popScreen = {}
    )
//    LoadingShimmerEffect()
}