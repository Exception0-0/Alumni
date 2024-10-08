package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.core.composable.CoverImageModifier
import dev.than0s.aluminium.core.composable.ProfileImageModifier
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit
) {
    ProfileScreenContent(
        userId = viewModel.profileScreenArgs.userId,
        userProfile = viewModel.userProfile,
        contactInfo = viewModel.contactInfo,
        editUserProfile = viewModel.editUserProfile,
        editContactInfo = viewModel.editContactInfo,
        openScreen = openScreen,
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
    userId: String,
    userProfile: User,
    contactInfo: ContactInfo,
    editUserProfile: User,
    editContactInfo: ContactInfo,
    openScreen: (Screen) -> Unit,
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

    AluminiumAsyncImage(
        model = userProfile.coverImage,
        settings = AluminiumAsyncImageSettings.CoverImage,
        modifier = CoverImageModifier.default
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
            .padding(top = MaterialTheme.spacing.extraLarge)
    ) {

        AluminiumAsyncImage(
            model = userProfile.profileImage,
            settings = AluminiumAsyncImageSettings.UserProfile,
            modifier = ProfileImageModifier.large
        )
        AluminiumTitleText(
            title = "${userProfile.firstName} ${userProfile.lastName}",
            fontSize = MaterialTheme.textSize.large,
        )
        AluminiumDescriptionText(
            description = userProfile.bio,
        )

        if (userId == currentUserId) {
            AluminiumElevatedButton(
                label = "Edit Profile",
                onClick = {
                    updateProfileDialogState = true
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        ProfileTabRow(
            userId = userId,
            tabItems = listOf(
                TabItem(
                    title = "Contacts",
                    selectedIcon = Icons.Filled.AccountBox,
                    unselectedIcon = Icons.Outlined.AccountBox,
                    screen = {
                        ContactsTabContent(
                            isCurrentUser = userId == currentUserId,
                            contactInfo = contactInfo,
                            editContactInfo = editContactInfo,
                            onEmailChange = onEmailChange,
                            onMobileChange = onMobileChange,
                            onSocialHandleChange = onSocialHandleChange,
                            onUpdateContactClick = onContactUpdateClick
                        )
                    }
                ),
            ),
            openScreen = openScreen,
        )
    }
}

@Composable
private fun ProfileTabRow(
    userId: String,
    tabItems: List<TabItem>,
    openScreen: (Screen) -> Unit
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
        Tab(
            selected = false,
            onClick = {
                openScreen(Screen.SpecificPostsScreen(userId))
            },
            text = {
                Text("Posts")
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.GridView,
                    contentDescription = "posts"
                )
            }
        )
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.small,
                vertical = MaterialTheme.spacing.large
            )
    ) {
        tabItems[tabRowStatus].screen.invoke()
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

    var circularProgressState by rememberSaveable { mutableStateOf(false) }

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
        Surface(
            shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Text(
                    text = "Profile",
                    fontSize = MaterialTheme.textSize.gigantic,
                    fontWeight = FontWeight.W900
                )
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
                        AluminiumTextField(
                            value = userProfile.firstName,
                            onValueChange = onFirstNameChange,
                            placeholder = "First Name",
                        )
                        AluminiumTextField(
                            value = userProfile.lastName,
                            onValueChange = onLastNameChange,
                            placeholder = "Last Name"
                        )
                    }
                }
                AluminiumTextField(
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

                    AluminiumLoadingTextButton(
                        label = "Update",
                        circularProgressIndicatorState = circularProgressState,
                        onClick = {
                            circularProgressState = true
                            onUpdateProfileClick(
                                {
                                    circularProgressState = false
                                    onDismiss()
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: @Composable () -> Unit
)

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        userId = "",
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
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
    )
}


private const val COVER_IMAGE = "cover_image"
private const val PROFILE_IMAGE = "profile_image"