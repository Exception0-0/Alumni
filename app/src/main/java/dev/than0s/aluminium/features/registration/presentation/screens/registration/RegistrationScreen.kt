package dev.than0s.aluminium.features.registration.presentation.screens.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import dev.than0s.aluminium.core.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    popAndOpen: (Screen) -> Unit,
) {
    RegistrationScreenContent(
        param = viewModel.param,
        categoryDialogState = viewModel.categoryDialogState.value,
        batchDialogState = viewModel.batchDialogState.value,
        onEmailChange = viewModel::onEmailChange,
        onRegisterClick = viewModel::onRegisterClick,
        popAndOpen = popAndOpen,
        onBatchDialogDismiss = viewModel::onBatchDialogDismiss,
        onCategoryDialogDismiss = viewModel::onCategoryDialogDismiss,
        onCategoryChange = viewModel::onCategoryChange,
        onRollNoChange = viewModel::onRollNoChange,
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
    popAndOpen: (Screen) -> Unit,
    onBatchDialogDismiss: () -> Unit,
    onCategoryDialogDismiss: () -> Unit,
    onCategoryChange: (String) -> Unit,
    onRollNoChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBatchChange: (String) -> Unit,
    onCategoryClick: () -> Unit,
    onBatchClick: () -> Unit
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

    AluminiumElevatedCard(
        modifier = Modifier
            .padding(MaterialTheme.spacing.large)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.large)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Registration",
                    fontSize = MaterialTheme.textSize.gigantic,
                )
            }

            AluminiumTextField(
                value = param.rollNo,
                onValueChange = { newValue ->
                    onRollNoChange(newValue)
                },
                placeholder = "Student ID / Staff ID"
            )

            AluminiumTextField(
                value = param.email,
                onValueChange = { newValue ->
                    onEmailChange(newValue)
                },
                placeholder = "Email"
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

            Row {

                TextField(
                    value = param.batch,
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Batch - Year")
                    },
                    enabled = false,
                    modifier = Modifier
                        .clickable {
                            onBatchClick()
                        }
                        .width(128.dp)
                )

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

                TextField(
                    value = param.category,
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Category")
                    },
                    enabled = false,
                    modifier = Modifier
                        .clickable {
                            onCategoryClick()
                        }
                        .width(128.dp),
                )
            }


            Text(
                text = "Already registered?",
                modifier = Modifier.clickable {
                    popAndOpen(Screen.SignInScreen)
                }
            )

            AluminiumElevatedButton(
                label = "Register",
                onClick = {
                    onRegisterClick()
                }
            )
        }
    }
}

@Composable
private fun LoadList(itemList: List<String>, onClick: (String) -> Unit, onDismiss: () -> Unit) {
    Card {
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
        false, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}


const val student = "Student"
const val staff = "Staff"
const val alumni = "Alumni"

private val categoryList = listOf(student, staff, alumni)
private val batchList = listOf("2023", "2022", "2021", "2020")
