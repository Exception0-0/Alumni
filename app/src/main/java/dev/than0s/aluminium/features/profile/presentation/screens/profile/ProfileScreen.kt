package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    openScreen: (String) -> Unit,
) {
    ProfileScreenContent(
        userProfile = viewModel.userProfile,
        openScreen = openScreen,
        onUpdateProfileClick = viewModel::onUpdateProfileClick,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBioChange = viewModel::onBioChange,
        onProfileImageChange = viewModel::onProfileImageChange,
    )
}

@Composable
private fun ProfileScreenContent(
    userProfile: User,
    openScreen: (String) -> Unit,
    onUpdateProfileClick: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
) {
    var updateProfileDialogState by rememberSaveable { mutableStateOf(false) }

    if (updateProfileDialogState) {
        UpdateProfileDialog(
            userProfile = userProfile,
            onUpdateProfileClick = onUpdateProfileClick,
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
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium)

        ) {

            AsyncImage(
                model = userProfile.profileImage,
                contentDescription = "user profile image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_background),
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

            DropdownMenuItem(
                text = {
                    Row {
                        Text(text = "Update Profile")
                    }
                },
                onClick = {
                    updateProfileDialogState = true
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = Icons.Default.Edit.name,
                    )
                }
            )

            DropdownMenuItem(
                text = {
                    Row {
                        Text(text = "My posts")
                    }
                },
                onClick = {
                    openScreen(Screen.MyPostsScreen.route)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "post add"
                    )
                }
            )

            DropdownMenuItem(
                text = {
                    Row {
                        Text(text = "Log Out")
                    }
                },
                onClick = {
                    openScreen(Screen.SignOutScreen.route)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = Icons.AutoMirrored.Filled.ExitToApp.name,
                    )
                }
            )
        }
    }
}

@Composable
fun UpdateProfileDialog(
    userProfile: User,
    onUpdateProfileClick: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onDismiss: () -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { image: Uri? ->
            image?.let {
                onProfileImageChange(it)
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
                    label = {
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
                        onUpdateProfileClick()
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
    ProfileScreenContent(
        User(
            id = "0",
            firstName = "Than0s",
            lastName = "Op",
            bio = "Hi I'm Than0s"
        ),
        {}, {}, {}, {}, {}, {},
    )
}