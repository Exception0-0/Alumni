package dev.than0s.aluminium.features.profile.presentation.screens.create_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.asString
import dev.than0s.aluminium.core.data.remote.COVER_IMAGE
import dev.than0s.aluminium.core.data.remote.PROFILE_IMAGE
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun CreateProfileScreen(
    viewModel: CreateProfileScreenViewModel = hiltViewModel(),
    restartApp: () -> Unit,
) {
    CreateProfileContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        restartApp = restartApp
    )
}

@Composable
private fun CreateProfileContent(
    screenState: CreateProfileScreenState,
    onEvent: (CreateProfileEvents) -> Unit,
    restartApp: () -> Unit,
) {
    val imageSelectionState = rememberSaveable {
        mutableMapOf(
            COVER_IMAGE to false,
            PROFILE_IMAGE to false,
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        imageUri?.let {
            imageSelectionState.let { map ->
                if (map[COVER_IMAGE]!!) {
                    onEvent(CreateProfileEvents.OnCoverImageChanged(it))
                    map[COVER_IMAGE] = false
                } else if (map[PROFILE_IMAGE]!!) {
                    onEvent(CreateProfileEvents.OnProfileImageChanged(it))
                    map[PROFILE_IMAGE] = false
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
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
            AsyncImage(
                model = screenState.userProfile.coverImage,
                contentDescription = "user profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(MaterialTheme.spacing.medium))
                    .background(color = colorResource(id = R.color.purple_500))
                    .height(100.dp)
                    .clickable {
                        imageSelectionState[COVER_IMAGE] = true
                        launcher.launch("image/*")
                    }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            ) {

                AsyncImage(
                    model = screenState.userProfile.profileImage,
                    contentDescription = "user profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            imageSelectionState[PROFILE_IMAGE] = true
                            launcher.launch("image/*")
                        }
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    AluminiumTextField(
                        value = screenState.userProfile.firstName,
                        onValueChange = {
                            onEvent(CreateProfileEvents.OnFirstNameChanged(it))
                        },
                        enable = !screenState.isLoading,
                        supportingText = screenState.firstNameError?.message?.asString(),
                        placeholder = "First Name",
                    )
                    AluminiumTextField(
                        value = screenState.userProfile.lastName,
                        enable = !screenState.isLoading,
                        onValueChange = {
                            onEvent(CreateProfileEvents.OnLastNameChanged(it))
                        },
                        supportingText = screenState.lastNameError?.message?.asString(),
                        placeholder = "Last Name"
                    )
                }
            }
            AluminiumTextField(
                value = screenState.userProfile.bio,
                onValueChange = {
                    onEvent(CreateProfileEvents.OnBioChanged(it))
                },
                enable = !screenState.isLoading,
                supportingText = screenState.bioError?.message?.asString(),
                placeholder = "Bio",
                modifier = Modifier.fillMaxWidth()
            )

            AluminiumLoadingElevatedButton(
                label = "Save",
                circularProgressIndicatorState = screenState.isLoading,
                onClick = {
                    onEvent(
                        CreateProfileEvents.OnProfileSaveClick(
                            restartApp = restartApp
                        )
                    )
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CreateProfilePreview() {
    CreateProfileContent(
        screenState = CreateProfileScreenState(),
        onEvent = {},
        restartApp = {}
    )
}