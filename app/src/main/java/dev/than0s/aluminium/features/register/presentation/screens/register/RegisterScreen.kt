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
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.presentation.screens.alumni
import dev.than0s.aluminium.features.register.presentation.screens.staff
import dev.than0s.aluminium.features.register.presentation.screens.student
import dev.than0s.mydiary.ui.spacing

@Composable
fun RegistrationScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    popAndOpen: (String) -> Unit,
    restartApp: () -> Unit
) {
    RegistrationScreenContent(
        param = viewModel.param.value,
        categoryDialogState = viewModel.categoryDialogState.value,
        batchDialogState = viewModel.batchDialogState.value,
        onEmailChange = viewModel::onEmailChange,
        onRegisterClick = viewModel::onRegisterClick,
        popAndOpen = popAndOpen,
        restartApp = restartApp,
        onBatchDialogDismiss = viewModel::onBatchDialogDismiss,
        onCategoryDialogDismiss = viewModel::onCategoryDialogDismiss,
        onCategoryChange = viewModel::onCategoryChange,
        onIdChange = viewModel::onIdChange,
        onFirstNameChange = viewModel::onFirstNameChange,
        onMiddleNameChange = viewModel::onMiddleNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBatchChange = viewModel::onBatchChange,
        onCategoryClick = viewModel::onCategoryClick,
        onBatchClick = viewModel::onBatchClick
    )
}

@Composable
private fun RegistrationScreenContent(
    param: RegistrationForm,
    categoryDialogState: Boolean,
    batchDialogState: Boolean,
    onEmailChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    popAndOpen: (String) -> Unit,
    restartApp: () -> Unit,
    onBatchDialogDismiss: () -> Unit,
    onCategoryDialogDismiss: () -> Unit,
    onCategoryChange: (String) -> Unit,
    onIdChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBatchChange: (String) -> Unit,
    onCategoryClick: () -> Unit,
    onBatchClick: () -> Unit
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (categoryDialogState) {
                Dialog(
                    onDismissRequest = { onCategoryDialogDismiss() },
                ) {
                    LoadCategoryList()
                }
            }

            if (batchDialogState) {
                Dialog(
                    onDismissRequest = { onBatchDialogDismiss() },
                ) {
                    LoadBatchList()
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
                readOnly = true,
                modifier = Modifier.clickable {
                    onCategoryClick()
                }
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
                readOnly = true,
                modifier = Modifier.clickable {
                    onBatchClick()
                }
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
fun LoadBatchList() {
    TODO("Not yet implemented")
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
private fun RegistrationScreenPreview() {
    RegistrationScreenContent(
        RegistrationForm(),
        false,
        false,
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {})
}