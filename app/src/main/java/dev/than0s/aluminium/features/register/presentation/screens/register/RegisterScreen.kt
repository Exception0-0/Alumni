package dev.than0s.aluminium.features.register.presentation.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.register.Param
import dev.than0s.aluminium.features.register.alumni
import dev.than0s.aluminium.features.register.staff
import dev.than0s.aluminium.features.register.student
import dev.than0s.mydiary.ui.spacing

@Composable
fun SignUpScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    popAndOpen: (String) -> Unit,
    restartApp: () -> Unit
) {
    SignUpScreenContent(
        param = viewModel.param.value,
        showDialog = viewModel.showDialog.value,
        onEmailChange = viewModel::onEmailChange,
        onRegisterClick = viewModel::onRegisterClick,
        popAndOpen = popAndOpen,
        restartApp = restartApp,
        onDialogDismiss = viewModel::onDialogDismiss,
        onCategoryChange = viewModel::onCategoryChange,
        onIdChange = viewModel::onIdChange,
        onFirstNameChange = viewModel::onFirstNameChange,
        onMiddleNameChange = viewModel::onMiddleNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBatchChange = viewModel::onBatchChange
    )
}

@Composable
private fun SignUpScreenContent(
    param: Param,
    showDialog: Boolean,
    onEmailChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    popAndOpen: (String) -> Unit,
    restartApp: () -> Unit,
    onDialogDismiss: () -> Unit,
    onCategoryChange: (String) -> Unit,
    onIdChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBatchChange: (String) -> Unit
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (showDialog) {
                Dialog(
                    onDismissRequest = { onDialogDismiss() },
                ) {
                    LoadCategoryList()
                }
            }

            TextField(
                value = param.category,
                onValueChange = { newValue ->
                    onCategoryChange(newValue)
                },
                placeholder = {
                    Text(text = "Category")
                },
                readOnly = true
            )

            TextField(
                value = param.id,
                onValueChange = { newValue ->
                    onIdChange(newValue)
                },
                placeholder = {
                    Text(text = "Student ID / Staff ID")
                }
            )

            TextField(
                value = param.email,
                onValueChange = { newValue ->
                    onEmailChange(newValue)
                },
                placeholder = {
                    Text(text = "Email")
                }
            )

            TextField(
                value = param.firstName,
                onValueChange = { newValue ->
                    onFirstNameChange(newValue)
                },
                placeholder = {
                    Text(text = "First Name")
                }
            )

            TextField(
                value = param.middleName,
                onValueChange = { newValue ->
                    onMiddleNameChange(newValue)
                },
                placeholder = {
                    Text(text = "Middle Name")
                }
            )

            TextField(
                value = param.lastName,
                onValueChange = { newValue ->
                    onLastNameChange(newValue)
                },
                placeholder = {
                    Text(text = "Last Name")
                }
            )

            TextField(
                value = param.batch,
                onValueChange = { newValue ->
                    onBatchChange(newValue)
                },
                placeholder = {
                    Text(text = "Batch - Year")
                },
                readOnly = true
            )


            Text(
                text = "Already registerd?",
                modifier = Modifier.clickable {
                    popAndOpen(Screen.SignInScreen.route)
                }
            )

            ElevatedButton(
                onClick = {
                    onRegisterClick()
                }
            ) {
                Text(text = "Register")
            }
        }
    }
}

@Composable
private fun LoadCategoryList() {
    Column {
        Text(text = student)
        Text(text = staff)
        Text(text = alumni)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreenContent(Param(), false, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}