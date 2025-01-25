package dev.than0s.aluminium.features.profile.presentation.dialogs.update_contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredSurface
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextField
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

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
    PreferredSurface(
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        if (screenState.isLoading) {
            LoadingShimmerEffect()
        } else {
            PreferredColumn(
                modifier = Modifier.padding(MaterialTheme.padding.medium)
            ) {
                Text(
                    text = "Update Contact",
                    fontSize = MaterialTheme.textSize.large,
                    fontWeight = FontWeight.Bold
                )
                PreferredTextField(
                    value = screenState.contactInfo.email ?: "",
                    onValueChange = {
                        onEvent(UpdateContactScreenEvents.OnEmailChanged(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null
                        )
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.emailError?.message?.asString(),
                    placeholder = "Email"
                )
                PreferredTextField(
                    value = screenState.contactInfo.mobile ?: "",
                    onValueChange = {
                        onEvent(UpdateContactScreenEvents.OnMobileChanged(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.mobileError?.message?.asString(),
                    placeholder = "Mobile"
                )
                PreferredTextField(
                    value = screenState.contactInfo.socialHandles ?: "",
                    onValueChange = {
                        onEvent(UpdateContactScreenEvents.OnSocialChanged(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null
                        )
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.socialHandleError?.message?.asString(),
                    placeholder = "Social Handles"
                )
                PreferredRow(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = popScreen,
                        enabled = !screenState.isUpdating
                    ) {
                        Text(text = "Cancel")
                    }

                    PreferredTextButton(
                        text = "Update",
                        isLoading = screenState.isUpdating,
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
}

@Composable
private fun LoadingShimmerEffect() {
    PreferredColumn(
        modifier = Modifier
            .padding(MaterialTheme.padding.medium)
            .shimmer()
    ) {
        ShimmerTextField(
            modifier = Modifier.fillMaxWidth()
        )
        ShimmerTextField(
            modifier = Modifier.fillMaxWidth()
        )
        ShimmerTextField(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateContactScreenPreview() {
//    UpdateContactScreenContent(
//        screenState = UpdateContactScreenState(),
//        onEvent = {},
//        popScreen = {}
//    )
    LoadingShimmerEffect()
}