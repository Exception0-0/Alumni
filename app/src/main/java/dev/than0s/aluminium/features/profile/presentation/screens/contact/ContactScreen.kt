package dev.than0s.aluminium.features.profile.presentation.screens.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerListItem
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

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
        PreferredColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
        ) {
            ListItem(
                headlineContent = {
                    Text(
                        text = "Email",
                        fontSize = MaterialTheme.textSize.medium,
                        fontWeight = FontWeight.Bold
                    )
                },
                supportingContent = {
                    Text(
                        text = screenState.contactInfo.email ?: "No email address added yet",
                        fontSize = MaterialTheme.textSize.medium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "email icon"
                    )
                },
            )
            ListItem(
                headlineContent = {
                    Text(
                        text = "Mobile",
                        fontSize = MaterialTheme.textSize.medium,
                        fontWeight = FontWeight.Bold
                    )
                },
                supportingContent = {
                    Text(
                        text = screenState.contactInfo.mobile ?: "No mobile number added yet",
                        fontSize = MaterialTheme.textSize.medium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "phone icon"
                    )
                },
            )
            ListItem(
                headlineContent = {
                    Text(
                        text = "Social Handles",
                        fontSize = MaterialTheme.textSize.medium,
                        fontWeight = FontWeight.Bold
                    )
                },
                supportingContent = {
                    Text(
                        text = screenState.contactInfo.socialHandles
                            ?: "No social handles added yet",
                        fontSize = MaterialTheme.textSize.medium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = "like icon"
                    )
                },
            )

            if (isCurrentUser) {
                PreferredFilledButton(
                    onClick = {
                        openScreen(Screen.UpdateContactDialog)
                    },
                    content = {
                        Text(text = "Edit Contacts")
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    PreferredColumn {
        for (i in 1..3) {
            ShimmerListItem()
        }
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