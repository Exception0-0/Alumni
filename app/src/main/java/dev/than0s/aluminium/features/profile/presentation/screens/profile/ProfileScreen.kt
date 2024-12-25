package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImageSettings
import dev.than0s.aluminium.core.presentation.composable.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingTextButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.composable.ProfileImageModifier
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.data.remote.COVER_IMAGE
import dev.than0s.aluminium.core.data.remote.PROFILE_IMAGE
import dev.than0s.aluminium.core.presentation.composable.AluminumLoading
import dev.than0s.aluminium.features.profile.presentation.screens.util.ProfileNavHost
import dev.than0s.aluminium.features.profile.presentation.screens.util.ProfileTabScreen
import dev.than0s.aluminium.features.profile.presentation.screens.util.replace
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
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        openScreen = openScreen
    )
}

@Composable
private fun ProfileScreenContent(
    userId: String,
    screenState: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
    openScreen: (Screen) -> Unit
) {
    if (screenState.updateProfileDialog) {
        UpdateProfileDialog(
            screenState = screenState,
            onEvent = onEvent
        )
    }

    if (screenState.isLoading) {
        AluminumLoading()
    } else {

        AsyncImage(
            model = screenState.user.coverImage,
            contentDescription = "Cover Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .background(color = colorResource(id = R.color.purple_500))
                .height(128.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
                .padding(top = MaterialTheme.spacing.extraLarge)
        ) {
            AluminiumAsyncImage(
                model = screenState.user.profileImage,
                settings = AluminiumAsyncImageSettings.UserProfile,
                modifier = ProfileImageModifier.large
            )
            AluminiumTitleText(
                title = "${screenState.user.firstName} ${screenState.user.lastName}",
                fontSize = MaterialTheme.textSize.large,
            )
            AluminiumDescriptionText(
                description = screenState.user.bio,
            )

            if (userId == currentUserId) {
                AluminiumElevatedButton(
                    label = "Edit Profile",
                    onClick = {
                        onEvent(ProfileEvents.OnEditProfileClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                AluminiumElevatedButton(
                    label = "Message",
                    onClick = {
                        openScreen(Screen.ChatDetailScreen(userId))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ProfileTabRow(
                screenState = screenState,
                onEvent = onEvent,
                openScreen = openScreen
            )
        }
    }
}

@Composable
private fun ProfileTabRow(
    screenState: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    val navController = rememberNavController()

    val tabItemList = rememberSaveable {
        listOf(
            TabItem(
                title = "About",
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
                screen = ProfileTabScreen.AboutScreen(
                    userId = screenState.user.id
                )
            ),
            TabItem(
                title = "Contacts",
                selectedIcon = Icons.Filled.AccountBox,
                unselectedIcon = Icons.Outlined.AccountBox,
                screen = ProfileTabScreen.ContactScreen(
                    userId = screenState.user.id
                )
            ),
            TabItem(
                title = "Posts",
                selectedIcon = Icons.Filled.GridView,
                unselectedIcon = Icons.Outlined.GridView,
                screen = ProfileTabScreen.PostsScreen(
                    userId = screenState.user.id
                )
            ),
        )
    }

    TabRow(
        selectedTabIndex = screenState.tabSelection
    ) {
        tabItemList.forEachIndexed { index, tabItem ->
            Tab(
                selected = index == screenState.tabSelection,
                onClick = {
                    onEvent(ProfileEvents.OnTabChanged(index))
                    navController.replace(tabItem.screen)
                },
                text = {
                    Text(text = tabItem.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == screenState.tabSelection) {
                            tabItem.selectedIcon
                        } else {
                            tabItem.unselectedIcon
                        },
                        contentDescription = tabItem.title
                    )
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.small,
                vertical = MaterialTheme.spacing.large
            )
    ) {
        ProfileNavHost(
            userId = screenState.user.id,
            navController = navController,
            openScreen = openScreen,
        )
    }
}


@Composable
private fun UpdateProfileDialog(
    screenState: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
) {
    val imageSelectionState = rememberSaveable {
        mutableMapOf(
            COVER_IMAGE to false,
            PROFILE_IMAGE to false,
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        imageUri?.let {
            imageSelectionState.let { map ->
                if (map[COVER_IMAGE]!!) {
                    onEvent(ProfileEvents.OnCoverImageChanged(it))
                    map[COVER_IMAGE] = false
                } else if (map[PROFILE_IMAGE]!!) {
                    onEvent(ProfileEvents.OnProfileImageChanged(it))
                    map[PROFILE_IMAGE] = false
                }
            }
        }
    }

    Dialog(
        onDismissRequest = {
            onEvent(ProfileEvents.OnUpdateDialogDismissRequest)
        }
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
                    model = screenState.dialogUser.coverImage,
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
                        model = screenState.dialogUser.profileImage,
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
                            value = screenState.dialogUser.firstName,
                            onValueChange = {
                                onEvent(ProfileEvents.OnFirstNameChanged(it))
                            },
                            enable = !screenState.isUpdating,
                            supportingText = screenState.firstNameError?.message?.asString(),
                            placeholder = "First Name",
                        )
                        AluminiumTextField(
                            value = screenState.dialogUser.lastName,
                            enable = !screenState.isUpdating,
                            onValueChange = {
                                onEvent(ProfileEvents.OnLastNameChanged(it))
                            },
                            supportingText = screenState.lastNameError?.message?.asString(),
                            placeholder = "Last Name"
                        )
                    }
                }
                AluminiumTextField(
                    value = screenState.dialogUser.bio,
                    onValueChange = {
                        onEvent(ProfileEvents.OnBioChanged(it))
                    },
                    enable = !screenState.isUpdating,
                    supportingText = screenState.bioError?.message?.asString(),
                    placeholder = "Bio",
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        onEvent(ProfileEvents.OnUpdateDialogDismissRequest)
                    }) {
                        Text(text = "Cancel")
                    }

                    AluminiumLoadingTextButton(
                        label = "Update",
                        circularProgressIndicatorState = screenState.isUpdating,
                        onClick = {
                            onEvent(ProfileEvents.OnProfileUpdateClick)
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
    val screen: ProfileTabScreen
)

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        userId = "",
        openScreen = {},
        screenState = ProfileState(),
        onEvent = {}
    )
}