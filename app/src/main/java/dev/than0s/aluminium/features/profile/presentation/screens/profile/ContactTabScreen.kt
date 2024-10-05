package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.than0s.aluminium.core.composable.LoadingTextButton
import dev.than0s.aluminium.core.composable.RoundedTextField
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun ContactsTabContent(
    contactInfo: ContactInfo,
    editContactInfo: ContactInfo,
    onEmailChange: (String) -> Unit,
    onMobileChange: (String) -> Unit,
    onSocialHandleChange: (String) -> Unit,
    onUpdateContactClick: (() -> Unit) -> Unit
) {
    var editStatus by rememberSaveable { mutableStateOf(false) }

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

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        InfoFormat(
            title = "Email",
            info = contactInfo.email,
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
        ElevatedButton(
            onClick = {
                editStatus = true
            },
        ) {
            Text(text = "Edit Contacts")
        }
    }

}

@Composable
private fun InfoFormat(
    title: String,
    info: String,
    icon: ImageVector,
) {
    ElevatedCard(
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
                Text(
                    text = title,
                    fontWeight = FontWeight.W500,
                    fontSize = MaterialTheme.textSize.large
                )

                Text(
                    text = info,
                    fontWeight = FontWeight.W300,
                    fontSize = MaterialTheme.textSize.small
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
                Text(
                    text = "Contact",
                    fontSize = MaterialTheme.textSize.gigantic,
                    fontWeight = FontWeight.W900
                )
                RoundedTextField(
                    value = contactInfo.email,
                    onValueChange = onEmailChange,
                    placeholder = "Email"
                )
                RoundedTextField(
                    value = contactInfo.mobile ?: "",
                    onValueChange = onPhoneChange,
                    placeholder = "Mobile"
                )
                RoundedTextField(
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

                    LoadingTextButton(
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