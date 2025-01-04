package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.utils.Screen

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
    restartApp: () -> Unit,
) {
    SettingScreenContent(
        onEvent = viewModel::onEvent,
        openScreen = openScreen,
        restartApp = restartApp,
    )
}

@Composable
private fun SettingScreenContent(
    onEvent: (SettingsEvents) -> Unit,
    openScreen: (Screen) -> Unit,
    restartApp: () -> Unit
) {
    PreferredColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ListItem(
            headlineContent = {
                Text(text = "Profile")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            modifier = Modifier.clickable {
                openScreen(Screen.ProfileScreen(currentUserId!!))
            }
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

@Preview(showSystemUi = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreenContent(
        onEvent = {},
        openScreen = {},
        restartApp = {}
    )
}