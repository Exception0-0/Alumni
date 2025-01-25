package dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.data.remote.COVER_IMAGE
import dev.than0s.aluminium.core.data.remote.PROFILE_IMAGE
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAddPicture
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredSurface
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerCover
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerProfileImage
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextField
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.coverHeight
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
import dev.than0s.aluminium.ui.roundedCorners
import dev.than0s.aluminium.ui.textSize

@Composable
fun UpdateProfileDialog(
    viewModel: UpdateProfileDialogViewModel = hiltViewModel(),
    shouldSignOutShow: Boolean = false,
    onSuccess: () -> Unit,
) {
    UpdateProfileDialogContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        shouldSignOutShow = shouldSignOutShow,
        onSuccess = onSuccess
    )
}

@Composable
private fun UpdateProfileDialogContent(
    screenState: UpdateProfileDialogState,
    shouldSignOutShow: Boolean,
    onSuccess: () -> Unit,
    onEvent: (UpdateProfileDialogEvents) -> Unit,
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

    PreferredSurface(
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        if (screenState.isLoading) {
            LoadingShimmerEffect()
        } else {
            PreferredColumn(
                modifier = Modifier.padding(MaterialTheme.padding.medium)
            ) {
                Text(
                    text = "Update Profile",
                    fontSize = MaterialTheme.textSize.large,
                    fontWeight = FontWeight.Bold
                )
                PreferredAddPicture(
                    model = screenState.userProfile.coverImage.let { if (it == Uri.EMPTY) null else it },
                    contentDescription = "user cover image",
                    shape = RoundedCornerShape(roundedCorners.default),
                    enabled = !screenState.isUpdating,
                    modifier = Modifier
                        .height(MaterialTheme.coverHeight.default)
                        .fillMaxWidth(),
                    onAddPicture = {
                        imageSelectionState[COVER_IMAGE] = true
                        pickMedia.launch(
                            input = PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            ),
                        )
                    }
                )
                PreferredRow {
                    PreferredAddPicture(
                        model = screenState.userProfile.profileImage.let { if (it == Uri.EMPTY) null else it },
                        contentDescription = "user profile image",
                        contentScale = ContentScale.Crop,
                        shape = CircleShape,
                        enabled = !screenState.isUpdating,
                        modifier = Modifier.size(MaterialTheme.profileSize.large),
                        onAddPicture = {
                            imageSelectionState[PROFILE_IMAGE] = true
                            pickMedia.launch(
                                input = PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                ),
                            )
                        },
                    )
                    PreferredColumn {
                        PreferredTextField(
                            value = screenState.userProfile.firstName,
                            onValueChange = {
                                onEvent(UpdateProfileDialogEvents.OnFirstNameChanged(it))
                            },
                            enable = !screenState.isUpdating,
                            supportingText = screenState.firstNameError?.message?.asString(),
                            placeholder = "First Name",
                        )
                        PreferredTextField(
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
                PreferredTextField(
                    value = screenState.userProfile.bio,
                    onValueChange = {
                        onEvent(UpdateProfileDialogEvents.OnBioChanged(it))
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.bioError?.message?.asString(),
                    placeholder = "Bio",
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PreferredRow(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        TextButton(
                            onClick = onSuccess,
                            enabled = !screenState.isUpdating
                        ) {
                            Text(text = "Cancel")
                        }
                        PreferredTextButton(
                            text = "Update",
                            isLoading = screenState.isUpdating,
                            onClick = {
                                onEvent(
                                    UpdateProfileDialogEvents.OnProfileUpdateClick(
                                        onSuccess = onSuccess
                                    )
                                )
                            }
                        )
                    }
                    if (shouldSignOutShow) {
                        PreferredRow(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                        ) {
                            PreferredTextButton(
                                text = "Sign out",
                                onClick = {
                                    onEvent(
                                        UpdateProfileDialogEvents.OnSignOutClick(
                                            restartApp = onSuccess
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    PreferredColumn(
        modifier = Modifier
            .padding(MaterialTheme.padding.medium)
            .shimmer()
    ) {
        ShimmerCover()
        PreferredRow {
            ShimmerProfileImage(
                size = MaterialTheme.profileSize.large
            )
            PreferredColumn {
                ShimmerTextField(
                    modifier = Modifier.fillMaxWidth()
                )
                ShimmerTextField(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        ShimmerTextField(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateProfileDialogPreview() {
    UpdateProfileDialogContent(
        screenState = UpdateProfileDialogState(),
        onEvent = {},
        onSuccess = {},
        shouldSignOutShow = false
    )
//    LoadingShimmerEffect()
}