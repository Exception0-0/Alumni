package dev.than0s.aluminium.features.registration.presentation.screens.registration

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumClickableText
import dev.than0s.aluminium.core.presentation.composable.AluminiumDropdownMenu
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingFilledButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumLottieAnimation
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    popScreen: () -> Unit,
) {
    RegistrationScreenContent(
        screenState = viewModel.screenState,
        popScreen = popScreen,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun RegistrationScreenContent(
    screenState: RegistrationState,
    popScreen: () -> Unit,
    onEvent: (RegistrationEvents) -> Unit,
) {
    val isLastIndex = screenState.formIndex == registrationFormSectionList.lastIndex
    val isIndexZero = screenState.formIndex == 0
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(MaterialTheme.spacing.large)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(MaterialTheme.spacing.large)
        ) {
            AluminiumTitleText(
                title = registrationFormSectionList[screenState.formIndex].name,
                fontSize = MaterialTheme.textSize.huge
            )

            registrationFormSectionList[screenState.formIndex].content(screenState, onEvent)

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                IconButton(
                    onClick = {
                        onEvent(RegistrationEvents.OnPreviousClick)
                    },
                    enabled = !isIndexZero,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "previous page"
                        )
                    }
                )

                IconButton(
                    onClick = {
                        onEvent(RegistrationEvents.OnNextClick)
                    },
                    enabled = !isLastIndex,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "next page"
                        )
                    }
                )
            }
            if (isLastIndex) {
                AluminiumLoadingFilledButton(
                    isLoading = screenState.isLoading,
                    enabled = !screenState.isLoading,
                    onClick = {
                        onEvent(
                            RegistrationEvents.OnRegisterClick(
                                onSuccess = popScreen
                            )
                        )
                    },
                    content = {
                        Text("Register")
                    }
                )
            }
        }
        AluminiumLottieAnimation(
            lottieAnimation = R.raw.registration_animation,
            modifier = Modifier.size(180.dp)
        )
    }
}


@Composable
private fun PersonalInfoSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
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

@Composable
private fun ContactInfoSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    AluminiumTextField(
        value = screenState.registrationForm.email,
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnEmailChange(newValue))
        },
        keyboardType = KeyboardType.Email,
        supportingText = screenState.emailError?.message?.asString(),
        placeholder = "Email"
    )
}

@Composable
private fun RoleSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    AluminiumDropdownMenu(
        value = screenState.registrationForm.role.name,
        placeHolder = "Role",
        dropdownList = Role.entries.minus(Role.Anonymous),
        onSelect = {
            onEvent(RegistrationEvents.OnRoleChange(it))
        }
    )
}

@Composable
private fun CollegeInfoSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onEvent(RegistrationEvents.OnCollegeIdCardChange(it))
            }
        }

    AluminiumTextField(
        value = screenState.registrationForm.collegeId ?: "",
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnCollegeIdChange(newValue))
        },
        keyboardType = KeyboardType.Number,
        supportingText = screenState.collageIdError?.message?.asString(),
        placeholder = "College Id"
    )

    if (screenState.registrationForm.role.let { it == Role.Student || it == Role.Alumni }) {
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
                keyboardType = KeyboardType.Number,
                supportingText = screenState.batchFromError?.message?.asString(),
                placeholder = "Batch - From",
                modifier = Modifier.weight(0.3f)
            )

            AluminiumTextField(
                value = screenState.registrationForm.batchTo ?: "",
                onValueChange = {
                    onEvent(RegistrationEvents.OnBatchToChange(it))
                },
                keyboardType = KeyboardType.Number,
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
        AluminiumAsyncImage(
            model = screenState.registrationForm.idCardImage,
            contentDescription = "Id card image",
            modifier = Modifier
                .size(128.dp)
                .clickable {
                    onEvent(RegistrationEvents.OnCollegeIdCardChange(null))
                }
        )
    }
}

private data class RegistrationFormSection(
    val name: String,
    val content: @Composable ((RegistrationState, (RegistrationEvents) -> Unit) -> Unit)
)

private val registrationFormSectionList = listOf(
    RegistrationFormSection(
        name = "Role",
        content = { state, events ->
            RoleSection(
                screenState = state,
                onEvent = events
            )
        }
    ),
    RegistrationFormSection(
        name = "College",
        content = { state, events ->
            CollegeInfoSection(
                screenState = state,
                onEvent = events
            )
        }
    ),
    RegistrationFormSection(
        name = "Personal",
        content = { state, events ->
            PersonalInfoSection(
                screenState = state,
                onEvent = events
            )
        }
    ),
    RegistrationFormSection(
        name = "Contact",
        content = { state, events ->
            ContactInfoSection(
                screenState = state,
                onEvent = events
            )
        }
    ),
)

@Preview(showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreenContent(
        screenState = RegistrationState(),
        onEvent = {},
        popScreen = {}
    )
}
