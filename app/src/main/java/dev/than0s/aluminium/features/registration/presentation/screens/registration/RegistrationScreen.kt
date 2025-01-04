package dev.than0s.aluminium.features.registration.presentation.screens.registration

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredClickableText
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredLottieAnimation
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.coverHeight
import dev.than0s.aluminium.ui.roundedCorners
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredColumn(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)
        ) {
            Text(
                text = registrationFormSectionList[screenState.formIndex].name,
                fontSize = MaterialTheme.textSize.large,
                fontWeight = FontWeight.Bold
            )
            registrationFormSectionList[screenState.formIndex].content(screenState, onEvent)

            PreferredRow {
                IconButton(
                    onClick = {
                        onEvent(RegistrationEvents.OnPreviousClick)
                    },
                    enabled = !isIndexZero && !screenState.isLoading,
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
                    enabled = !isLastIndex && !screenState.isLoading,
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "next page"
                        )
                    }
                )
            }
            if (isLastIndex) {
                PreferredFilledButton(
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
            PreferredLottieAnimation(
                lottieAnimation = R.raw.registration_animation,
                modifier = Modifier.size(180.dp)
            )
        }
    }
}


@Composable
private fun PersonalInfoSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    PreferredTextField(
        value = screenState.registrationForm.firstName,
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnFirstNameChange(newValue))
        },
        enable = !screenState.isLoading,
        supportingText = screenState.firstNameError?.message?.asString(),
        placeholder = "First Name"
    )

    PreferredTextField(
        value = screenState.registrationForm.middleName,
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnMiddleNameChange(newValue))
        },
        enable = !screenState.isLoading,
        supportingText = screenState.middleNameError?.message?.asString(),
        placeholder = "Middle Name"
    )

    PreferredTextField(
        value = screenState.registrationForm.lastName,
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnLastNameChange(newValue))
        },
        enable = !screenState.isLoading,
        supportingText = screenState.lastNameError?.message?.asString(),
        placeholder = "Last Name"
    )
}

@Composable
private fun ContactInfoSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    PreferredTextField(
        value = screenState.registrationForm.email,
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnEmailChange(newValue))
        },
        enable = !screenState.isLoading,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "mail icon"
            )
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
    Box {
        PreferredTextField(
            value = screenState.registrationForm.role.name,
            onValueChange = {},
            placeholder = "Role",
            enable = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.WorkOutline,
                    contentDescription = "role icon"
                )
            },
            trailingIcon = {
                if (screenState.roleExpanded) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = "drop up arrow"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "drop down arrow"
                    )
                }
            },
            modifier = Modifier.clickable(
                enabled = !screenState.isLoading,
                onClick = {
                    onEvent(RegistrationEvents.OnRoleClick)
                }
            )
        )
        DropdownMenu(
            expanded = screenState.roleExpanded,
            onDismissRequest = {
                onEvent(RegistrationEvents.OnRoleClick)
            }
        ) {
            Role.entries.minus(Role.Anonymous).forEach {
                DropdownMenuItem(
                    text = {
                        Text(it.name)
                    },
                    onClick = {
                        onEvent(RegistrationEvents.OnRoleChange(it))
                        onEvent(RegistrationEvents.OnRoleClick)
                    }
                )
            }
        }
    }
}

@Composable
private fun CollegeInfoSection(
    screenState: RegistrationState,
    onEvent: (RegistrationEvents) -> Unit
) {
    val pickMedia =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { image: Uri? ->
            image?.let {
                onEvent(RegistrationEvents.OnCollegeIdCardChange(it))
            }
        }

    PreferredTextField(
        value = screenState.registrationForm.collegeId,
        onValueChange = { newValue ->
            onEvent(RegistrationEvents.OnCollegeIdChange(newValue))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Badge,
                contentDescription = "course icon"
            )
        },
        enable = !screenState.isLoading,
        keyboardType = KeyboardType.Number,
        supportingText = screenState.collageIdError?.message?.asString(),
        placeholder = "College Id"
    )

    if (screenState.registrationForm.role.let { it == Role.Student || it == Role.Alumni }) {
        Box {
            PreferredTextField(
                value = screenState.registrationForm.course?.name ?: Course.MCA.name,
                onValueChange = {},
                placeholder = "Course",
                enable = false,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = "course icon"
                    )
                },
                trailingIcon = {
                    if (screenState.courseExpanded) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropUp,
                            contentDescription = "drop up arrow"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "drop down arrow"
                        )
                    }
                },
                modifier = Modifier.clickable(
                    enabled = !screenState.isLoading,
                    onClick = {
                        onEvent(RegistrationEvents.OnCourseClick)
                    }
                )
            )
            DropdownMenu(
                expanded = screenState.courseExpanded,
                onDismissRequest = {
                    onEvent(RegistrationEvents.OnCourseClick)
                }
            ) {
                Course.entries.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it.name)
                        },
                        onClick = {
                            onEvent(RegistrationEvents.OnCourseChange(it))
                            onEvent(RegistrationEvents.OnCourseClick)
                        }
                    )
                }
            }
        }
    }

    if (screenState.registrationForm.role == Role.Alumni) {
        PreferredRow {
            PreferredTextField(
                value = screenState.registrationForm.batchFrom ?: "",
                onValueChange = {
                    onEvent(RegistrationEvents.OnBatchFromChange(it))
                },
                keyboardType = KeyboardType.Number,
                enable = !screenState.isLoading,
                supportingText = screenState.batchFromError?.message?.asString(),
                placeholder = "Batch - From",
            )

            PreferredTextField(
                value = screenState.registrationForm.batchTo ?: "",
                onValueChange = {
                    onEvent(RegistrationEvents.OnBatchToChange(it))
                },
                keyboardType = KeyboardType.Number,
                enable = !screenState.isLoading,
                supportingText = screenState.batchToError?.message?.asString(),
                placeholder = "Batch - To",
            )
        }
    }

    if (screenState.registrationForm.idCardImage == null) {
        PreferredClickableText(
            text = "Add college Id image (optional)",
            onClick = {
                pickMedia.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageAndVideo
                    )
                )
            }
        )
    } else {
        PreferredAsyncImage(
            model = screenState.registrationForm.idCardImage,
            contentDescription = "Id card image",
            modifier = Modifier
                .size(MaterialTheme.coverHeight.default)
                .clip(RoundedCornerShape(MaterialTheme.roundedCorners.default))
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
