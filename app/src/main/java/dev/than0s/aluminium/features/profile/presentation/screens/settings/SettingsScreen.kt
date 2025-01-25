package dev.than0s.aluminium.features.profile.presentation.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Abc
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredIconButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredSurface
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWarningDialog
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
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
    screenState: StateSettingsScreen,
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
        if (currentUserRole != Role.Admin) {
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
        }
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
                    SettingsEvents.ShowLogoutDialog
                )
            }
        )
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.about))
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "info"
                )
            },
            modifier = Modifier.clickable {
                onEvent(SettingsEvents.ShowAboutDialog)
            }
        )
    }
    if (screenState.isLogoutDialogShown) {
        PreferredWarningDialog(
            title = stringResource(R.string.log_out_title),
            description = stringResource(R.string.log_out_description),
            onDismissRequest = {
                onEvent(
                    SettingsEvents.DismissLogoutDialog
                )
            },
            onConfirmation = {
                onEvent(
                    SettingsEvents.OnSignOut(
                        restartApp = restartApp
                    )
                )
            },
        )
    }
    AnimatedVisibility(
        visible = screenState.isAboutDialogShown
    ) {
        AboutDialog(
            onDismissRequest = {
                onEvent(SettingsEvents.DismissAboutDialog)
            }
        )
    }
}

@Composable
private fun AboutDialog(
    onDismissRequest: () -> Unit
) {
    val localUriHandler = LocalUriHandler.current
    val localContext = LocalContext.current
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        PreferredSurface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            PreferredColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.padding.extraLarge,
                    vertical = MaterialTheme.padding.medium
                )
            ) {
                Image(
                    imageVector = Icons.Outlined.Abc,
                    contentDescription = "Application Logo",
                    modifier = Modifier
                        .size(MaterialTheme.profileSize.large)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { }
                )
                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.textSize.large
                )
                Text(
                    text = "by Than0s",
                    fontSize = MaterialTheme.textSize.medium,
                )
                PreferredRow {
                    PreferredIconButton(
                        icon = painterResource(R.drawable.linktree_icon),
                        onClick = {
                            localUriHandler.openUri(
                                localContext.resources.getString(R.string.linktree_url)
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreenContent(
        screenState = StateSettingsScreen(),
        onEvent = {},
        openScreen = {},
        restartApp = {}
    )
}