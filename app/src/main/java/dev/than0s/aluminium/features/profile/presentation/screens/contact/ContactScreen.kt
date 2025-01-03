package dev.than0s.aluminium.features.profile.presentation.screens.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumElevatedButton
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormat
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormatShimmer
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding

@Composable
fun ContactScreen(
    viewModel: ContactViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
) {
    ContactsContent(
        isCurrentUser = viewModel.contactScreenArgs.userId == currentUserId,
        screenState = viewModel.screenState,
        openScreen = openScreen,
        onEvents = viewModel::onEvent
    )
}

@Composable
private fun ContactsContent(
    isCurrentUser: Boolean,
    screenState: ContactState,
    openScreen: (Screen) -> Unit,
    onEvents: (ContactEvents) -> Unit
) {
    if (screenState.isLoading) {
        LoadingShimmerEffect()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.large),
        ) {
            CardInfoFormat(
                title = "Email",
                info = screenState.contactInfo.email ?: "No email address added yet",
                icon = Icons.Outlined.Email,
            )
            CardInfoFormat(
                title = "Mobile",
                info = screenState.contactInfo.mobile ?: "No mobile number added yet",
                icon = Icons.Outlined.Phone,
            )
            CardInfoFormat(
                title = "Social Handles",
                info = screenState.contactInfo.socialHandles ?: "No social handles added yet",
                icon = Icons.Outlined.Link,
            )

            if (isCurrentUser) {
                AluminiumElevatedButton(
                    label = "Edit Contacts",
                    onClick = {
                        openScreen(Screen.UpdateContactDialog)
                    },
                )
            }
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.large)
    ) {
        CardInfoFormatShimmer()
        CardInfoFormatShimmer()
        CardInfoFormatShimmer()
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ContactScreenPreview() {
    ContactsContent(
        isCurrentUser = true,
        screenState = ContactState(),
        openScreen = {},
        onEvents = {}
    )
}