package dev.than0s.aluminium.features.profile.presentation.screens.profile

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.core.composable.ShimmerBackground
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun ContactsTabContent(
    isCurrentUser: Boolean,
    contactInfo: ContactInfo,
    editContactInfo: ContactInfo,
    isContactsLoading:Boolean,
    onEmailChange: (String) -> Unit,
    onMobileChange: (String) -> Unit,
    onSocialHandleChange: (String) -> Unit,
    onUpdateContactClick: (() -> Unit) -> Unit
) {
    var editStatus by rememberSaveable { mutableStateOf(false) }

    if (isCurrentUser) {
        if (editStatus) {
            UpdateContactInfo(
                contactInfo = editContactInfo,
                onEmailChange = onEmailChange,
                onPhoneChange = onMobileChange,
                onSocialHandleChange = onSocialHandleChange,
                onUpdateContactClick = onUpdateContactClick,
                onDismiss = {
                    editStatus = false
                }
            )
        }
    }

    if(isContactsLoading){
        ShimmerContacts()
    }
    else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            InfoFormat(
                title = "Email",
                info = contactInfo.email ?: "No email address added yet",
                icon = Icons.Outlined.Email,
            )
            InfoFormat(
                title = "Mobile",
                info = contactInfo.mobile ?: "No mobile number added",
                icon = Icons.Outlined.Phone,
            )
            InfoFormat(
                title = "Social Handles",
                info = contactInfo.socialHandles ?: "No social handles added",
                icon = Icons.Outlined.Star,
            )

            if (isCurrentUser) {
                AluminiumElevatedButton(
                    label = "Edit Contacts",
                    onClick = {
                        editStatus = true
                    },
                )
            }
        }
    }
}

@Composable
private fun ShimmerContacts() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .shimmer()
    ) {
        ShimmerBackground(
            modifier = Modifier
                .height(MaterialTheme.Size.medium)
                .fillMaxWidth()
        )
        ShimmerBackground(
            modifier = Modifier
                .height(MaterialTheme.Size.medium)
                .fillMaxWidth()
        )
        ShimmerBackground(
            modifier = Modifier
                .height(MaterialTheme.Size.medium)
                .fillMaxWidth()
        )

        ShimmerBackground(
            modifier = Modifier
                .height(MaterialTheme.Size.small)
                .width(MaterialTheme.Size.small)
        )
    }
}

@Composable
fun InfoFormat(
    title: String,
    info: String,
    icon: ImageVector,
) {
    AluminiumElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                AluminiumTitleText(
                    title = title,
                    fontSize = MaterialTheme.textSize.large
                )

                AluminiumDescriptionText(
                    description = info,
                )
            }
        }
    }
}

@Composable
private fun UpdateContactInfo(
    contactInfo: ContactInfo,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSocialHandleChange: (String) -> Unit,
    onUpdateContactClick: (() -> Unit) -> Unit,
    onDismiss: () -> Unit
) {
    var circularProgressState by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss
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
                    onValueChange = onEmailChange,
                    placeholder = "Email"
                )
                AluminiumTextField(
                    value = contactInfo.mobile ?: "",
                    onValueChange = onPhoneChange,
                    placeholder = "Mobile"
                )
                AluminiumTextField(
                    value = contactInfo.socialHandles ?: "",
                    onValueChange = onSocialHandleChange,
                    placeholder = "Social Handles"
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }

                    AluminiumLoadingTextButton(
                        label = "Update",
                        circularProgressIndicatorState = circularProgressState,
                        onClick = {
                            circularProgressState = true
                            onUpdateContactClick {
                                circularProgressState = false
                                onDismiss()
                            }
                        }
                    )
                }
            }
        }

    }
}