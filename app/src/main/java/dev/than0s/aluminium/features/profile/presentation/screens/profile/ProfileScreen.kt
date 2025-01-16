package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredCircularProgressIndicator
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredCircularProgressIndicatorSize
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFullScreen
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredPinchZoom
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.replace
import dev.than0s.aluminium.features.profile.presentation.screens.util.ProfileNavHost
import dev.than0s.aluminium.ui.coverHeight
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.profileSize
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
    if (screenState.fullScreenImage != null) {
        PreferredFullScreen(
            title = "Image",
            onDismissRequest = {
                onEvent(ProfileEvents.DismissFullScreenImage)
            },
            content = {
                PreferredPinchZoom {
                    PreferredAsyncImage(
                        model = screenState.fullScreenImage,
                        contentScale = ContentScale.Fit,
                        contentDescription = "Post Image",
                    )
                }
            }
        )
    }
    if (screenState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            PreferredCircularProgressIndicator(
                size = PreferredCircularProgressIndicatorSize.default,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        PreferredColumn(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileAndCoverShower(
                screenState = screenState,
                onImageClick = { uri: Uri ->
                    onEvent(ProfileEvents.ShowFullScreenImage(uri))
                }
            )
            PreferredColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.verySmall),
                modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium)
            ) {
                Text(
                    text = "${screenState.user.firstName} ${screenState.user.lastName}",
                    fontSize = MaterialTheme.textSize.large,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = screenState.user.bio,
                    fontSize = MaterialTheme.textSize.medium,
                    modifier = Modifier.fillMaxWidth()
                )

                if (userId == currentUserId) {
                    PreferredFilledButton(
                        onClick = {
                            openScreen(Screen.UpdateProfileDialog)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Text(text = "Edit Profile")
                        }
                    )
                } else {
                    PreferredFilledButton(
                        onClick = {
//                            openScreen(Screen.ChatDetailScreen(userId,))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Text(text = "Message")
                        }
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
}

@Composable
private fun ProfileTabRow(
    screenState: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(key1 = currentBackStackEntry) {
        onEvent(
            ProfileEvents.OnTabChanged(
                getSelectedIndexOfTabRow(currentBackStackEntry?.destination)
            )
        )
    }

    TabRow(
        selectedTabIndex = screenState.tabRowSelectedIndex
    ) {
        tabRowItemList.forEachIndexed { index, tabItem ->
            val isSelected = index == screenState.tabRowSelectedIndex
            Tab(
                selected = isSelected,
                onClick = {
                    navController.replace(tabItem.screen(screenState.user.id))
                },
                text = {
                    Text(text = tabItem.title)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
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
            .padding(MaterialTheme.padding.extraSmall)
    ) {
        ProfileNavHost(
            userId = screenState.user.id,
            navController = navController,
            openScreen = openScreen,
        )
    }
}

@Composable
private fun ProfileAndCoverShower(
    screenState: ProfileState,
    onImageClick: (Uri) -> Unit,
) {
    Box {
        PreferredAsyncImage(
            model = screenState.user.coverImage,
            contentDescription = "Cover Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.coverHeight.default)
                .clickable {
                    onImageClick(screenState.user.coverImage)
                }
        )
        PreferredAsyncImage(
            model = screenState.user.profileImage,
            shape = CircleShape,
            contentDescription = "Profile Image",
            modifier = Modifier
                .padding(
                    start = MaterialTheme.padding.medium,
                    top = MaterialTheme.coverHeight.default - (MaterialTheme.profileSize.large / 2)
                )
                .size(MaterialTheme.profileSize.large)
                .clickable {
                    onImageClick(screenState.user.profileImage)
                }
        )
    }
}

private fun getSelectedIndexOfTabRow(destination: NavDestination?): Int {
    return when {
        destination == null -> 0
        destination.hasRoute<Screen.ProfileTabScreen.AboutScreen>() -> 0
        destination.hasRoute<Screen.ProfileTabScreen.ContactScreen>() -> 1
        destination.hasRoute<Screen.ProfileTabScreen.PostsScreen>() -> 2
        else -> 0
    }
}

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: (String) -> Screen,
)

private val tabRowItemList = listOf(
    TabItem(
        title = Screen.ProfileTabScreen.AboutScreen("").name,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        screen = { userId ->
            Screen.ProfileTabScreen.AboutScreen(
                userId = userId
            )
        }
    ),
    TabItem(
        title = Screen.ProfileTabScreen.ContactScreen("").name,
        selectedIcon = Icons.Filled.Contacts,
        unselectedIcon = Icons.Outlined.Contacts,
        screen = { userId ->
            Screen.ProfileTabScreen.ContactScreen(
                userId = userId
            )
        }
    ),
    TabItem(
        title = Screen.ProfileTabScreen.PostsScreen("").name,
        selectedIcon = Icons.Filled.GridOn,
        unselectedIcon = Icons.Outlined.GridOn,
        screen = { userId ->
            Screen.ProfileTabScreen.PostsScreen(
                userId = userId
            )
        }
    ),
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