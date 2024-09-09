package dev.than0s.aluminium.features.settings.presentation.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), restartApp: () -> Unit) {
    viewModel.userFlow.collectAsState(User()).value?.let {
        viewModel.userProfile = it
    }
    ProfileScreenContent(
        userProfile = viewModel.userProfile,
        onSignOutClick = viewModel::onSignOutClick,
        onUpdateClick = viewModel::updateProfile,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBioChange = viewModel::onBioChange,
        onProfileImageChange = viewModel::onProfileImageChange,
        restartApp = restartApp,
    )
}

@Composable
private fun ProfileScreenContent(
    userProfile: User,
    onSignOutClick: (() -> Unit) -> Unit,
    onUpdateClick: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    restartApp: () -> Unit,
) {
    var updateProfileDialogState by rememberSaveable { mutableStateOf(false) }

    if (updateProfileDialogState) {
        UpdateProfileDialog(
            userProfile = userProfile,
            onUpdateClick = onUpdateClick,
            onFirstNameChange = onFirstNameChange,
            onLastNameChange = onLastNameChange,
            onBioChange = onBioChange,
            onProfileImageChange = onProfileImageChange,
            onDismiss = {
                updateProfileDialogState = false
            },
        )
    }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            modifier = Modifier
                .fillMaxWidth()

        ) {

            IconButton(
                onClick = {
                    onSignOutClick(restartApp)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = Icons.AutoMirrored.Filled.ExitToApp.name
                )
            }

            AsyncImage(
                model = userProfile.profileImage,
                contentDescription = "user profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "${userProfile.firstName} ${userProfile.lastName}",
                fontSize = MaterialTheme.textSize.gigantic,
                fontWeight = FontWeight.W400
            )
            Text(
                text = userProfile.bio,
                modifier = Modifier.width(256.dp),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = Icons.Default.Edit.name,
                    modifier = Modifier.clickable {
                        updateProfileDialogState = true
                    }
                )
            }
        }
    }
}

@Composable
fun UpdateProfileDialog(
    userProfile: User,
    onUpdateClick: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onDismiss: () -> Unit,
) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.run {
                onProfileImageChange(this)
            }
        }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                AsyncImage(
                    model = userProfile.profileImage,
                    contentDescription = "user profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
                TextField(
                    value = userProfile.firstName,
                    onValueChange = { newValue ->
                        onFirstNameChange(newValue)
                    },
                    placeholder = {
                        Text(text = "First Name")
                    }
                )
                TextField(
                    value = userProfile.lastName,
                    onValueChange = { newValue ->
                        onLastNameChange(newValue)
                    },
                    placeholder = {
                        Text(text = "Last Name")
                    }
                )
                TextField(
                    value = userProfile.bio,
                    onValueChange = { newValue ->
                        onBioChange(newValue)
                    },
                    placeholder = {
                        Text(text = "Bio")
                    },
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }

                    TextButton(onClick = {
                        onUpdateClick()
                        onDismiss()
                    }) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(User(), {}, {}, {}, {}, {}, {}, {})
}