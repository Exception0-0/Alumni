package dev.than0s.aluminium.features.profile.presentation.screens.settings

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
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
) {
    SettingScreenContent(
        userProfile = viewModel.userProfile,
        openScreen = openScreen,

        )
}

@Composable
private fun SettingScreenContent(
    userProfile: User,
    openScreen: (Screen) -> Unit,

    ) {

    val listOfSettingsOptions = remember {
        listOf(
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

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)

        ) {

            ElevatedCard(
                onClick = {
                    openScreen(Screen.ProfileScreen(currentUserId!!))
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
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

//            DropdownMenuItem(
//                text = {
//                    Row {
//                        Text(text = "My posts")
//                    }
//                },
//                onClick = {
//                    openScreen(Screen.PostsScreen(currentUserId))
//                },
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.List,
//                        contentDescription = "post add"
//                    )
//                }
//            )
            listOfSettingsOptions.forEach { option ->

                ElevatedCard(
                    onClick = {
                        option.onClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
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

data class SettingsOptions(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Preview(showSystemUi = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreenContent(
        User(
            firstName = "Than0s",
            lastName = "Op",
            bio = "Hi I'm Than0s"
        ),
        {}
    )
}