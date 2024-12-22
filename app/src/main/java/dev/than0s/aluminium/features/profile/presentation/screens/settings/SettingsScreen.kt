package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.domain.data_class.User
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
) {
    initOptionList(openScreen)
    SettingScreenContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        openScreen = openScreen,
    )
}

fun initOptionList(openScreen: (Screen) -> Unit) {
    settingsOptionList = listOf(
        if (currentUserRole != Role.Admin) {
            SettingsOptions(
                title = "Add Post",
                icon = Icons.Default.AddAPhoto,
                onClick = {
                    openScreen(Screen.PostUploadScreen)
                }
            )
        } else
        SettingsOptions(
            title = "Security",
            icon = Icons.Default.Security,
            onClick = {

            }
        ),
        SettingsOptions(
            title = "Log Out",
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            onClick = {
                openScreen(Screen.SignOutScreen)
            }
        )
    )
}


@Composable
private fun SettingScreenContent(
    screenState: SettingsState,
    onEvent: (SettingsEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)

        ) {

            if (currentUserRole != Role.Admin) {
                ProfileCard(
                    userProfile = screenState.user,
                    isProfileLoading = screenState.isLoading,
                    openScreen = openScreen,
                )
            }

            settingsOptionList.forEach { option ->

                AluminiumElevatedCard(
                    onClick = {
                        option.onClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
                        modifier = Modifier.padding(MaterialTheme.spacing.medium)

                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = option.icon.name,
                        )
                        Spacer(
                            modifier = Modifier.width(MaterialTheme.spacing.medium)
                        )
                        Text(text = option.title)
                    }
                }
            }
        }
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
                AsyncImage(
                    model = userProfile.profileImage,
                    contentDescription = "user profile image",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
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
                        .height(MaterialTheme.textSize.gigantic.value.dp)
                        .width(MaterialTheme.Size.medium)
                )
                ShimmerBackground(
                    modifier = Modifier
                        .height(MaterialTheme.textSize.small.value.dp)
                        .width(MaterialTheme.Size.small)
                )
            }
        }
    }
}

var settingsOptionList: List<SettingsOptions> = emptyList()

data class SettingsOptions(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Preview(showSystemUi = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreenContent(
        screenState = SettingsState(),
        onEvent = {},
        openScreen = {}
    )
}