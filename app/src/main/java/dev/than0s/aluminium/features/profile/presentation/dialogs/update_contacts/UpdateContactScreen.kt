package dev.than0s.aluminium.features.profile.presentation.dialogs.update_contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumSurface
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.spacing

@Composable
fun UpdateContactScreen(
    viewModel: UpdateContactScreenViewModel = hiltViewModel(),
    popScreen: () -> Unit,
) {
    UpdateContactScreenContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        popScreen = popScreen
    )
}

@Composable
private fun UpdateContactScreenContent(
    screenState: UpdateContactScreenState,
    onEvent: (UpdateContactScreenEvents) -> Unit,
    popScreen: () -> Unit,
) {
    AluminiumSurface {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            AluminiumTitleText(
                title = "Contact",
            )
            AluminiumTextField(
                value = screenState.contactInfo.email ?: "",
                onValueChange = {
                    onEvent(UpdateContactScreenEvents.OnEmailChanged(it))
                },
                enable = !screenState.isUpdating,
                supportingText = screenState.emailError?.message?.asString(),
                placeholder = "Email"
            )
            AluminiumTextField(
                value = screenState.contactInfo.mobile ?: "",
                onValueChange = {
                    onEvent(UpdateContactScreenEvents.OnMobileChanged(it))
                },
                enable = !screenState.isUpdating,
                supportingText = screenState.mobileError?.message?.asString(),
                placeholder = "Mobile"
            )
            AluminiumTextField(
                value = screenState.contactInfo.socialHandles ?: "",
                onValueChange = {
                    onEvent(UpdateContactScreenEvents.OnSocialChanged(it))
                },
                enable = !screenState.isUpdating,
                supportingText = screenState.socialHandleError?.message?.asString(),
                placeholder = "Social Handles"
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = popScreen) {
                    Text(text = "Cancel")
                }

                AluminiumLoadingTextButton(
                    label = "Update",
                    circularProgressIndicatorState = screenState.isUpdating,
                    onClick = {
                        onEvent(
                            UpdateContactScreenEvents.OnUpdateClick(
                                onSuccess = popScreen
                            )
                        )
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateContactScreenPreview() {
    UpdateContactScreenContent(
        screenState = UpdateContactScreenState(),
        onEvent = {},
        popScreen = {}
    )
}