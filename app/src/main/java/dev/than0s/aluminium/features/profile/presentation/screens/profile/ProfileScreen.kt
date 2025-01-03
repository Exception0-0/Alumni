package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumDescriptionText
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumElevatedButton
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.composable.preferred.AluminumCircularLoading
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.replace
import dev.than0s.aluminium.features.profile.presentation.screens.util.ProfileNavHost
import dev.than0s.aluminium.ui.coverHeight
import dev.than0s.aluminium.ui.padding
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
    if (screenState.isLoading) {
        AluminumCircularLoading()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileAndCoverShower(
                screenState = screenState
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.padding.medium
                    )
            ) {
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
                            openScreen(Screen.UpdateProfileDialog)
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
}

@Composable
private fun ProfileTabRow(
    screenState: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
    openScreen: (Screen) -> Unit,
) {
    val navController = rememberNavController()

    TabRow(
        selectedTabIndex = 0
    ) {
        tabRowItemList.forEachIndexed { index, tabItem ->
            val isSelected = index == 0
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
            .padding(MaterialTheme.padding.small)
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
    screenState: ProfileState
) {
    Box {
        AluminiumAsyncImage(
            model = screenState.user.coverImage,
            contentDescription = "Cover Image",
            onTapFullScreen = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.coverHeight.default)
                .align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier.padding(
                top = 84.dp,
                start = MaterialTheme.padding.medium
            )
        ) {
            AluminiumAsyncImage(
                model = screenState.user.profileImage,
                onTapFullScreen = true,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopStart)
            )
        }
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
        selectedIcon = Icons.Filled.AccountBox,
        unselectedIcon = Icons.Outlined.AccountBox,
        screen = { userId ->
            Screen.ProfileTabScreen.ContactScreen(
                userId = userId
            )
        }
    ),
    TabItem(
        title = Screen.ProfileTabScreen.PostsScreen("").name,
        selectedIcon = Icons.Filled.GridView,
        unselectedIcon = Icons.Outlined.GridView,
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