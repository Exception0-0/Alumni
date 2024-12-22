package dev.than0s.aluminium.features.registration.presentation.screens.registration

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.asString
import dev.than0s.aluminium.core.presentation.composable.AluminiumClickableText
import dev.than0s.aluminium.core.presentation.composable.AluminiumDropdownMenu
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    popAndOpen: (Screen) -> Unit,
) {
    RegistrationScreenContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        popAndOpen = popAndOpen,
    )
}

@Composable
private fun RegistrationScreenContent(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit,
    popAndOpen: (Screen) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.small)
            .verticalScroll(rememberScrollState())
    ) {

        RoleCard(
            param = screenState.registrationForm,
            onEvent = onEvent
        )

        if (screenState.registrationForm.role != Role.Admin) {
            CollegeInfoCard(
                screenState = screenState,
                onEvent = onEvent
            )
        }

        PersonalInfoCard(
            screenState = screenState,
            onEvent = onEvent
        )

        ContactInfoCard(
            screenState = screenState,
            onEvent = onEvent
        )

        AluminiumClickableText(
            title = "Already registered?",
            onClick = {
                popAndOpen(Screen.SignInScreen)
            }
        )

        AluminiumLoadingElevatedButton(
            label = "Register",
            circularProgressIndicatorState = screenState.isLoading,
            onClick = {
                onEvent(RegistrationEvents.OnRegisterClick)
            }
        )
    }
}


@Composable
private fun PersonalInfoCard(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    AluminiumElevatedCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(MaterialTheme.spacing.large)
        ) {

            AluminiumTitleText(
                title = "Personal",
                fontSize = MaterialTheme.textSize.huge
            )

            AluminiumTextField(
                value = screenState.registrationForm.firstName,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvents.OnFirstNameChange(newValue))
                },
                supportingText = screenState.firstNameError?.message?.asString(),
                placeholder = "First Name"
            )

            AluminiumTextField(
                value = screenState.registrationForm.middleName,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvents.OnMiddleNameChange(newValue))
                },
                supportingText = screenState.middleNameError?.message?.asString(),
                placeholder = "Middle Name"
            )

            AluminiumTextField(
                value = screenState.registrationForm.lastName,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvents.OnLastNameChange(newValue))
                },
                supportingText = screenState.lastNameError?.message?.asString(),
                placeholder = "Last Name"
            )
        }
    }
}

@Composable
private fun ContactInfoCard(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    AluminiumElevatedCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(MaterialTheme.spacing.large)
        ) {

            AluminiumTitleText(
                title = "Contacts",
                fontSize = MaterialTheme.textSize.huge
            )

            AluminiumTextField(
                value = screenState.registrationForm.email,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvents.OnEmailChange(newValue))
                },
                supportingText = screenState.emailError?.message?.asString(),
                placeholder = "Email"
            )
        }
    }
}

@Composable
private fun RoleCard(
    param: RegistrationForm,
    onEvent: (RegistrationEvents) -> Unit
) {

    AluminiumElevatedCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(MaterialTheme.spacing.large)
        ) {

            AluminiumTitleText(
                title = "Select Role",
                fontSize = MaterialTheme.textSize.huge
            )

            AluminiumDropdownMenu(
                value = param.role.name,
                placeHolder = "Role",
                dropdownList = Role.entries,
                onSelect = {
                    onEvent(RegistrationEvents.OnRoleChange(it))
                }
            )
        }
    }
}

@Composable
private fun CollegeInfoCard(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onEvent(RegistrationEvents.OnCollegeIdCardChange(it))
            }
        }

    AluminiumElevatedCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(MaterialTheme.spacing.large)
        ) {

            AluminiumTitleText(
                title = "College",
                fontSize = MaterialTheme.textSize.huge
            )

            AluminiumTextField(
                value = screenState.registrationForm.collegeId ?: "",
                onValueChange = { newValue ->
                    onEvent(RegistrationEvents.OnCollegeIdChange(newValue))
                },
                supportingText = screenState.collageIdError?.message?.asString(),
                placeholder = "College Id"
            )

            if (screenState.registrationForm.role == Role.Student) {
                AluminiumDropdownMenu(
                    value = screenState.registrationForm.course?.name ?: Course.MCA.name,
                    placeHolder = "Course",
                    dropdownList = Course.entries,
                    onSelect = {
                        onEvent(RegistrationEvents.OnCourseChange(it))
                    }
                )
            }

            if (screenState.registrationForm.role == Role.Alumni) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                ) {
                    AluminiumTextField(
                        value = screenState.registrationForm.batchFrom ?: "",
                        onValueChange = {
                            onEvent(RegistrationEvents.OnBatchFromChange(it))
                        },
                        supportingText = screenState.batchFromError?.message?.asString(),
                        placeholder = "Batch - From",
                        modifier = Modifier.weight(0.3f)
                    )

                    AluminiumTextField(
                        value = screenState.registrationForm.batchTo ?: "",
                        onValueChange = {
                            onEvent(RegistrationEvents.OnBatchToChange(it))
                        },
                        supportingText = screenState.batchToError?.message?.asString(),
                        placeholder = "Batch - To",
                        modifier = Modifier.weight(0.3f)
                    )
                }
            }

            if (screenState.registrationForm.idCardImage == null) {
                AluminiumClickableText(
                    title = "Add college Id image (optional)",
                    onClick = {
                        launcher.launch("image/*")
                    }
                )
            } else {
                AsyncImage(
                    model = screenState.registrationForm.idCardImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Id card image",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(MaterialTheme.roundCorners.default))
                        .clickable {
                            onEvent(RegistrationEvents.OnCollegeIdCardChange(null))
                        }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreenContent(
        screenState = RegistrationState(),
        onEvent = {},
        popAndOpen = {}
    )
}
