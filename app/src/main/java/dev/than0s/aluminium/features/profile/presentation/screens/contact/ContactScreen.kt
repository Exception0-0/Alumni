package dev.than0s.aluminium.features.profile.presentation.screens.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.composable.AluminumLoading
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormat
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing

@Composable
fun ContactScreen(
    viewModel: ContactViewModel = hiltViewModel(),
    userId: String
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.userId = userId
        viewModel.onEvent(ContactEvents.LoadContactInfo)
    }
    ContactsContent(
        isCurrentUser = viewModel.userId == currentUserId,
        screenState = viewModel.screenState,
        onEvents = viewModel::onEvent
    )
}

@Composable
private fun ContactsContent(
    isCurrentUser: Boolean,
    screenState: ContactState,
    onEvents: (ContactEvents) -> Unit
) {
    if (isCurrentUser && screenState.updateDialog) {
        UpdateContactInfo(
            contactInfo = screenState.contactInfo,
            screenState = screenState,
            onEvents = onEvents
        )
    }

    if (screenState.isLoading) {
        AluminumLoading()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            CardInfoFormat(
                title = "Email",
                info = screenState.contactInfo.email ?: "No email address added yet",
                icon = Icons.Outlined.Email,
            )
            CardInfoFormat(
                title = "Mobile",
                info = screenState.contactInfo.mobile ?: "No mobile number added",
                icon = Icons.Outlined.Phone,
            )
            CardInfoFormat(
                title = "Social Handles",
                info = screenState.contactInfo.socialHandles ?: "No social handles added",
                icon = Icons.Outlined.Star,
            )

            if (isCurrentUser) {
                AluminiumElevatedButton(
                    label = "Edit Contacts",
                    onClick = {
                        onEvents(ContactEvents.OnContactInfoEditClick)
                    },
                )
            }
        }
    }
}

//@Composable
//private fun ShimmerContacts() {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//            .shimmer()
//    ) {
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.small)
//                .fillMaxWidth()
//        )
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.small)
//                .fillMaxWidth()
//        )
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.small)
//                .fillMaxWidth()
//        )
//
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.extraSmall)
//                .width(MaterialTheme.Size.small)
//        )
//    }
//}

@Composable
private fun UpdateContactInfo(
    contactInfo: ContactInfo,
    screenState: ContactState,
    onEvents: (ContactEvents) -> Unit
) {

    Dialog(
        onDismissRequest = {
            onEvents(ContactEvents.OnUpdateDialogDismissRequest)
        }
    ) {
        Surface(
            shape = RoundedCornerShape(MaterialTheme.roundCorners.default)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                AluminiumTitleText(
                    title = "Contact",
                )
                AluminiumTextField(
                    value = contactInfo.email ?: "",
                    onValueChange = {
                        onEvents(ContactEvents.OnEmailChanged(it))
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.emailError?.message?.asString(),
                    placeholder = "Email"
                )
                AluminiumTextField(
                    value = contactInfo.mobile ?: "",
                    onValueChange = {
                        onEvents(ContactEvents.OnMobileChanged(it))
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.mobileError?.message?.asString(),
                    placeholder = "Mobile"
                )
                AluminiumTextField(
                    value = contactInfo.socialHandles ?: "",
                    onValueChange = {
                        onEvents(ContactEvents.OnSocialChanged(it))
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.socialHandleError?.message?.asString(),
                    placeholder = "Social Handles"
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        onEvents(ContactEvents.OnUpdateDialogDismissRequest)
                    }) {
                        Text(text = "Cancel")
                    }

                    AluminiumLoadingTextButton(
                        label = "Update",
                        circularProgressIndicatorState = screenState.isUpdating,
                        onClick = {
                            onEvents(ContactEvents.OnUpdateClick)
                        }
                    )
                }
            }
        }

    }
}