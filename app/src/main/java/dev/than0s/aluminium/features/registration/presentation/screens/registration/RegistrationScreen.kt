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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.than0s.aluminium.core.composable.AluminiumClickableText
import dev.than0s.aluminium.core.composable.AluminiumDropdownMenu
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.composable.AluminiumTitleText
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
        param = viewModel.param,
        popAndOpen = popAndOpen,
        onEmailChange = viewModel::onEmailChange,
        onRegisterClick = viewModel::onRegisterClick,
        onRoleChange = viewModel::onRoleChange,
        onRollNoChange = viewModel::onCollegeIdChange,
        onFirstNameChange = viewModel::onFirstNameChange,
        onMiddleNameChange = viewModel::onMiddleNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBatchFromChange = viewModel::onBatchFromChange,
        onBatchToChange = viewModel::onBatchToChange,
        onCollegeIdChange = viewModel::onCollegeIdChange,
        onCourseChange = viewModel::onCourseChange
    )
}

@Composable
private fun RegistrationScreenContent(
    param: RegistrationForm,
    onEmailChange: (String) -> Unit,
    onRegisterClick: (() -> Unit) -> Unit,
    popAndOpen: (Screen) -> Unit,
    onRoleChange: (Role) -> Unit,
    onCourseChange: (Course) -> Unit,
    onRollNoChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBatchFromChange: (String) -> Unit,
    onBatchToChange: (String) -> Unit,
    onCollegeIdChange: (Uri?) -> Unit,
) {
    var formIndex by rememberSaveable { mutableIntStateOf(0) }
    var circularProgressIndicatorState by rememberSaveable { mutableStateOf(false) }

    val list: MutableList<@Composable () -> Unit> = mutableListOf()

    list.add {
        RoleCard(
            param = param,
            onRoleChange = onRoleChange
        )
    }
    if (param.role != Role.Admin) {
        list.add {
            CollegeInfoCard(
                param = param,
                onRollNoChange = onRollNoChange,
                onBatchToChange = onBatchToChange,
                onBatchFromChange = onBatchFromChange,
                onCollegeIdChange = onCollegeIdChange,
                onCourseChange = onCourseChange
            )
        }
    }
    list.add {
        PersonalInfoCard(
            param = param,
            onFirstNameChange = onFirstNameChange,
            onLastNameChange = onLastNameChange,
            onMiddleNameChange = onMiddleNameChange
        )
    }
    list.add {
        ContactInfoCard(
            param = param,
            onEmailChange = onEmailChange,
        )
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.large)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface {
                list[formIndex]()
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraLarge)
            ) {
                if (formIndex > 0) {
                    FilledIconButton(
                        onClick = {
                            formIndex--
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Next"
                            )
                        }
                    )
                }
                if (formIndex < list.size - 1) {
                    FilledIconButton(
                        onClick = {
                            formIndex++
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next"
                            )
                        }
                    )
                }
            }

            AluminiumClickableText(
                title = "Already registered?",
                onClick = {
                    popAndOpen(Screen.SignInScreen)
                }
            )

            if (formIndex == list.size - 1) {
                AluminiumLoadingElevatedButton(
                    label = "Register",
                    circularProgressIndicatorState = circularProgressIndicatorState,
                    onClick = {
                        circularProgressIndicatorState = true
                        onRegisterClick {
                            circularProgressIndicatorState = false
                            popAndOpen(Screen.SignInScreen)
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun PersonalInfoCard(
    param: RegistrationForm,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
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
                value = param.firstName,
                onValueChange = { newValue ->
                    onFirstNameChange(newValue)
                },
                placeholder = "First Name"
            )

            AluminiumTextField(
                value = param.middleName,
                onValueChange = { newValue ->
                    onMiddleNameChange(newValue)
                },
                placeholder = "Middle Name"
            )

            AluminiumTextField(
                value = param.lastName,
                onValueChange = { newValue ->
                    onLastNameChange(newValue)
                },
                placeholder = "Last Name"
            )
        }
    }
}

@Composable
private fun ContactInfoCard(
    param: RegistrationForm,
    onEmailChange: (String) -> Unit,
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
                value = param.email,
                onValueChange = { newValue ->
                    onEmailChange(newValue)
                },
                placeholder = "Email"
            )
        }
    }
}

@Composable
private fun RoleCard(
    param: RegistrationForm,
    onRoleChange: (Role) -> Unit,
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
                onSelect = onRoleChange
            )
        }
    }
}

@Composable
private fun CollegeInfoCard(
    param: RegistrationForm,
    onRollNoChange: (String) -> Unit,
    onBatchFromChange: (String) -> Unit,
    onBatchToChange: (String) -> Unit,
    onCollegeIdChange: (Uri?) -> Unit,
    onCourseChange: (Course) -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onCollegeIdChange(it)
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
                value = param.collegeId ?: "",
                onValueChange = { newValue ->
                    onRollNoChange(newValue)
                },
                placeholder = "College Id"
            )

            if (param.role != Role.Staff) {
                AluminiumDropdownMenu(
                    value = param.course?.name ?: Course.MCA.name,
                    placeHolder = "Course",
                    dropdownList = Course.entries,
                    onSelect = onCourseChange
                )
            }

            if (param.role == Role.Alumni) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                ) {
                    AluminiumTextField(
                        value = param.batchFrom ?: "",
                        onValueChange = {
                            onBatchFromChange(it)
                        },
                        placeholder = "Batch - From",
                        modifier = Modifier.weight(0.3f)
                    )

                    AluminiumTextField(
                        value = param.batchTo ?: "",
                        onValueChange = {
                            onBatchToChange(it)
                        },
                        placeholder = "Batch - To",
                        modifier = Modifier.weight(0.3f)
                    )
                }
            }

            if (param.idCardImage == null) {
                AluminiumClickableText(
                    title = "Add college Id image (optional)",
                    onClick = {
                        launcher.launch("image/*")
                    }
                )
            } else {
                AsyncImage(
                    model = param.idCardImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Id card image",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(MaterialTheme.roundCorners.default))
                        .clickable {
                            onCollegeIdChange(null)
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
        RegistrationForm(
            idCardImage = Uri.EMPTY
        ),
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
    )
}
