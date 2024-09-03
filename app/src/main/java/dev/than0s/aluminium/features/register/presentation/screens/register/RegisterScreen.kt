package dev.than0s.aluminium.features.register.presentation.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.presentation.screens.alumni
import dev.than0s.aluminium.features.register.presentation.screens.staff
import dev.than0s.aluminium.features.register.presentation.screens.student
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun RegistrationScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    popAndOpen: (String) -> Unit,
) {
    RegistrationScreenContent(
        param = viewModel.param.value,
        categoryDialogState = viewModel.categoryDialogState.value,
        batchDialogState = viewModel.batchDialogState.value,
        onEmailChange = viewModel::onEmailChange,
        onRegisterClick = viewModel::onRegisterClick,
        popAndOpen = popAndOpen,
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
                    LoadList(categoryList, onCategoryChange, onCategoryDialogDismiss)
                }
            }

            if (batchDialogState) {
                Dialog(
                    onDismissRequest = { onBatchDialogDismiss() },
                ) {
                    LoadList(batchList, onBatchChange, onBatchDialogDismiss)
                }
            }


            TextField(
                value = param.category,
                onValueChange = {},
                placeholder = {
                    Text(text = "Category")
                },
                modifier = Modifier.clickable {
                    onCategoryClick()
                },
                enabled = false
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
                onValueChange = {},
                placeholder = {
                    Text(text = "Batch - Year")
                },
                enabled = false,
                modifier = Modifier.clickable {
                    onBatchClick()
                }
            )


            Text(
                text = "Already registered?",
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
private fun LoadList(itemList: List<String>, onClick: (String) -> Unit, onDismiss: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(MaterialTheme.spacing.large)) {
            items(items = itemList) { item ->
                Text(
                    text = item,
                    fontSize = MaterialTheme.textSize.extraLarge,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.extraSmall)
                        .clickable {
                            onClick(item)
                            onDismiss()
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
        {})
}

private val categoryList = listOf(student, staff, alumni)
private val batchList = listOf("2023", "2022", "2021", "2020")