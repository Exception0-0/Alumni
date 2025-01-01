package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.core.presentation.composable.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumGroupTitle
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
    restartApp: () -> Unit,
) {
    SettingScreenContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        openScreen = openScreen,
        restartApp = restartApp,
    )
}

@Composable
private fun SettingScreenContent(
    screenState: SettingsState,
    onEvent: (SettingsEvents) -> Unit,
    openScreen: (Screen) -> Unit,
    restartApp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
            .verticalScroll(rememberScrollState())
    ) {

        if (currentUserRole != Role.Admin) {
            AluminiumGroupTitle(
                title = "profile"
            )

            ProfileCard(
                userProfile = screenState.user,
                isProfileLoading = screenState.isLoading,
                openScreen = openScreen,
            )
        }
        AluminiumGroupTitle(
            title = "options"
        )
        ListItem(
            headlineContent = {
                Text(text = "Appearance")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Palette,
                    contentDescription = "Appearance"
                )
            },
            modifier = Modifier.clickable {
                openScreen(Screen.AppearanceScreen)
            }
        )
        ListItem(
            headlineContent = {
                Text(text = "Log out")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Log out"
                )
            },
            modifier = Modifier.clickable {
                onEvent(
                    SettingsEvents.OnSignOut(
                        restartApp = restartApp
                    )
                )
            }
        )
    }
}

@Composable
private fun ProfileCard(
    userProfile: User,
    isProfileLoading: Boolean,
    openScreen: (Screen) -> Unit,
) {
    if (isProfileLoading) {
        ShimmerProfileCard()
    } else {
        AluminiumElevatedCard(
            onClick = {
                openScreen(Screen.ProfileScreen(currentUserId!!))
            }
        ) {
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                AluminiumAsyncImage(
                    model = userProfile.profileImage,
                    contentDescription = "user profile image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                ) {
                    Text(
                        text = "${userProfile.firstName} ${userProfile.lastName}",
                        fontSize = MaterialTheme.textSize.gigantic,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = userProfile.bio,
                        modifier = Modifier.width(256.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerProfileCard() {
    AluminiumElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer()
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            ShimmerBackground(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                ShimmerBackground(
                    modifier = Modifier
                        .height(MaterialTheme.Size.extraSmall)
                        .width(MaterialTheme.Size.medium)
                )
                ShimmerBackground(
                    modifier = Modifier
                        .height(16.dp)
                        .width(MaterialTheme.Size.large)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreenContent(
        screenState = SettingsState(),
        onEvent = {},
        openScreen = {},
        restartApp = {}
    )
}