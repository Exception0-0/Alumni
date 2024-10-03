package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.composable.RoundedTextField
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    ProfileScreenContent(
        userProfile = viewModel.userProfile,
        contactInfo = viewModel.contactInfo,
        editUserProfile = viewModel.editUserProfile,
        editContactInfo = viewModel.editContactInfo,
        onUpdateProfileClick = viewModel::onUpdateProfileClick,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onBioChange = viewModel::onBioChange,
        onProfileImageChange = viewModel::onProfileImageChange,
        onCoverImageChange = viewModel::onCoverImageChange,
        onEmailChange = viewModel::onEmailChange,
        onMobileChange = viewModel::onMobileChange,
        onSocialHandleChange = viewModel::onSocialHandleChange,
        onContactUpdateClick = viewModel::onContactInfoUpdateClick
    )
}

@Composable
private fun ProfileScreenContent(
    userProfile: User,
    contactInfo: ContactInfo,
    editUserProfile: User,
    editContactInfo: ContactInfo,
    onUpdateProfileClick: (() -> Unit) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onCoverImageChange: (Uri) -> Unit,
    onEmailChange: (String) -> Unit,
    onMobileChange: (String) -> Unit,
    onSocialHandleChange: (String) -> Unit,
    onContactUpdateClick: (() -> Unit) -> Unit
) {

    var updateProfileDialogState by rememberSaveable { mutableStateOf(false) }

    if (updateProfileDialogState) {
        UpdateProfileDialog(
            userProfile = editUserProfile,
            onUpdateProfileClick = onUpdateProfileClick,
            onFirstNameChange = onFirstNameChange,
            onLastNameChange = onLastNameChange,
            onBioChange = onBioChange,
            onProfileImageChange = onProfileImageChange,
            onCoverImageChange = onCoverImageChange,
            onDismiss = {
                updateProfileDialogState = false
            },
        )
    }

    AsyncImage(
        model = userProfile.coverImage,
        contentDescription = "cover image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(128.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.purple_500))
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
            .padding(top = MaterialTheme.spacing.extraLarge)
    ) {

        AsyncImage(
            model = userProfile.profileImage,
            contentDescription = "user profile image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Text(
            text = "${userProfile.firstName} ${userProfile.lastName}",
            fontSize = MaterialTheme.textSize.large,
            fontWeight = FontWeight.W500
        )
        Text(
            text = userProfile.bio,
            fontSize = MaterialTheme.textSize.small,
            fontWeight = FontWeight.W300
        )
        ElevatedButton(
            onClick = {
                updateProfileDialogState = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Edit Profile")
        }

        ProfileTabRow(
            listOf({
                ContactsTabContent(
                    contactInfo = editContactInfo,
                    onEmailChange = onEmailChange,
                    onMobileChange = onMobileChange,
                    onSocialHandleChange = onSocialHandleChange,
                    onUpdateContactClick = onContactUpdateClick
                )
            },
                {
                    ContactsTabContent(
                        contactInfo = editContactInfo,
                        onEmailChange = onEmailChange,
                        onMobileChange = onMobileChange,
                        onSocialHandleChange = onSocialHandleChange,
                        onUpdateContactClick = onContactUpdateClick
                    )
                }
            )
        )
    }
}

@Composable
private fun ProfileTabRow(
    contentScreens: List<@Composable () -> Unit>
) {
    var tabRowStatus by rememberSaveable { mutableIntStateOf(0) }

    TabRow(
        selectedTabIndex = tabRowStatus
    ) {
        tabItems.forEachIndexed { index, tabItem ->
            Tab(
                selected = index == tabRowStatus,
                onClick = {
                    tabRowStatus = index
                },
                text = {
                    Text(text = tabItem.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == tabRowStatus) tabItem.selectedIcon else tabItem.unselectedIcon,
                        contentDescription = tabItem.title
                    )
                }
            )
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.small,
                vertical = MaterialTheme.spacing.large
            )
    ) {
        contentScreens[tabRowStatus].invoke()
    }
}


@Composable
private fun UpdateProfileDialog(
    userProfile: User,
    onUpdateProfileClick: (() -> Unit) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onCoverImageChange: (Uri) -> Unit,
    onDismiss: () -> Unit,
) {
    val imageSelectionState = mutableMapOf(
        COVER_IMAGE to false,
        PROFILE_IMAGE to false,
    )

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        )
        { imageUri ->
            imageUri?.let {
                imageSelectionState.let { map ->
                    if (map[COVER_IMAGE]!!) {
                        onCoverImageChange(it)
                        map[COVER_IMAGE] = false
                    } else if (map[PROFILE_IMAGE]!!) {
                        onProfileImageChange(it)
                        map[PROFILE_IMAGE] = false
                    }
                }
            }
        }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Surface {
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                ) {
                    AsyncImage(
                        model = userProfile.coverImage,
                        contentDescription = "user profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(MaterialTheme.spacing.medium))
                            .background(color = colorResource(id = R.color.purple_500))
                            .height(100.dp)
                            .clickable {
                                imageSelectionState[COVER_IMAGE] = true
                                launcher.launch("image/*")
                            }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    ) {

                        AsyncImage(
                            model = userProfile.profileImage,
                            contentDescription = "user profile image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .clickable {
                                    imageSelectionState[PROFILE_IMAGE] = true
                                    launcher.launch("image/*")
                                }
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                        ) {
                            RoundedTextField(
                                value = userProfile.firstName,
                                onValueChange = onFirstNameChange,
                                placeholder = "First Name",
                            )
                            RoundedTextField(
                                value = userProfile.lastName,
                                onValueChange = onLastNameChange,
                                placeholder = "Last Name"
                            )
                        }
                    }
                    RoundedTextField(
                        value = userProfile.bio,
                        onValueChange = onBioChange,
                        placeholder = "Bio",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(text = "Cancel")
                        }

                        TextButton(onClick = {
                            onUpdateProfileClick(onDismiss)
                        }) {
                            Text(text = "Update")
                        }
                    }
                }
            }
        }
    }
}

private val tabItems = listOf(
    TabItem(
        title = "Contacts",
        selectedIcon = Icons.Filled.AccountBox,
        unselectedIcon = Icons.Outlined.AccountBox
    ),
    TabItem(
        title = "Professional",
        selectedIcon = Icons.Filled.Build,
        unselectedIcon = Icons.Outlined.Build
    ),
)

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        User(
            firstName = "Than0s",
            lastName = "Op",
            bio = "Hi I'm Than0s, nice to meet you"
        ),
        ContactInfo(
            email = "thanosop150@gmail.com",
            mobile = "+91-1234567890"
        ),
        User(
            firstName = "Than0s",
            lastName = "Op",
            bio = "Hi I'm Than0s, nice to meet you"
        ),
        ContactInfo(
            email = "thanosop150@gmail.com",
            mobile = "+91-1234567890"
        ),
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
    )
}


private const val COVER_IMAGE = "cover_image"
private const val PROFILE_IMAGE = "profile_image"