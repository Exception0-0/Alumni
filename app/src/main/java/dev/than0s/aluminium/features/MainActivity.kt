package dev.than0s.aluminium.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AppRegistration
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.asString
import dev.than0s.aluminium.core.presentation.composable.ObserveAsEvents
import dev.than0s.aluminium.core.currentUserRole
import dev.than0s.aluminium.features.auth.presentation.screens.sign_in.SignInScreen
import dev.than0s.aluminium.features.post.presentation.screens.post_upload.PostUploadScreen
import dev.than0s.aluminium.features.splash.presentation.splash.SplashScreen
import dev.than0s.aluminium.features.registration.presentation.screens.registration.RegistrationScreen
import dev.than0s.aluminium.features.registration.presentation.screens.registration_requests.RegistrationRequestsScreen
import dev.than0s.aluminium.features.auth.presentation.screens.forget_password.ForgetPasswordScreen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_out.SignOutScreen
import dev.than0s.aluminium.features.post.presentation.screens.comments.CommentScreen
import dev.than0s.aluminium.features.post.presentation.screens.posts.PostsScreen
import dev.than0s.aluminium.features.profile.presentation.screens.create_profile.CreateProfileScreen
import dev.than0s.aluminium.features.profile.presentation.screens.profile.ProfileScreen
import dev.than0s.aluminium.features.profile.presentation.screens.settings.SettingScreen
import dev.than0s.aluminium.ui.theme.AluminiumTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AluminiumTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                SnackbarLogic(snackbarHostState)

                Scaffold(
                    topBar = {
                        AluminiumTopAppBar(
                            navController = navController,
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    bottomBar = {
                        AluminiumBottomNavigationBar(navController)
                    },
                ) { paddingValue ->
                    NavGraphHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValue)
                    )
                }
            }
        }
    }
}

@Composable
private fun SnackbarLogic(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message.asString(context),
                actionLabel = event.action?.name?.asString(context),
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
}

@Composable
private fun NavGraphHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen,
        modifier = modifier
    ) {
        composable<Screen.SplashScreen> {
            SplashScreen(
                popAndOpen = navController::popAndOpen
            )
        }
        composable<Screen.SignInScreen> {
            SignInScreen(
                openScreen = navController::openScreen,
                popAndOpen = navController::popAndOpen,
                restartApp = navController::restartApp
            )
        }
        composable<Screen.RegistrationScreen> {
            RegistrationScreen(
                popAndOpen = navController::popAndOpen
            )
        }
        composable<Screen.SettingScreen> {
            SettingScreen(
                openScreen = navController::openScreen,
            )
        }
        composable<Screen.RegistrationRequestsScreen> {
            RegistrationRequestsScreen()
        }
        composable<Screen.PostUploadScreen> {
            PostUploadScreen(
                popScreen = navController::popScreen
            )
        }
        composable<Screen.SignOutScreen> {
            SignOutScreen(
                restartApp = navController::restartApp
            )
        }
        composable<Screen.ForgotPasswordScreen> {
            ForgetPasswordScreen(
                popScreen = navController::popScreen
            )
        }
        composable<Screen.PostsScreen> {
            PostsScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.ProfileScreen> {
            ProfileScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.CommentsScreen> {
            CommentScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.CreateProfileScreen> {
            CreateProfileScreen(
                restartApp = navController::restartApp
            )
        }
        composable<Screen.ChatListScreen> {
//            ChatListScreen(
//                openScreen = navController::openScreen
//            )
        }
        composable<Screen.ChatDetailScreen> {
//            ChatDetailScreen()
        }
    }
}

fun NavHostController.openScreen(screen: Screen) {
    navigate(screen)
}

private fun NavHostController.popScreen() {
    popBackStack()
}

private fun NavHostController.popAndOpen(screen: Screen) {
    popBackStack()
    navigate(screen)
}

private fun NavHostController.restartApp() {
    navigate(Screen.SplashScreen) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
}

@Composable
private fun AluminiumBottomNavigationBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItemsList = mutableListOf<BottomNavigationItem>()
    bottomNavItemsList.apply {

        if (currentUserRole == Role.Admin) {
            add(
                BottomNavigationItem(
                    "dev.than0s.aluminium.core.Screen.RegistrationRequestsScreen",
                    Screen.RegistrationRequestsScreen,
                    Icons.Filled.AppRegistration,
                    Icons.Outlined.AppRegistration,
                    "Registration Request"
                )
            )
        } else {
            add(
                BottomNavigationItem(
                    "dev.than0s.aluminium.core.Screen.PostsScreen?userId={userId}",
                    Screen.PostsScreen(),
                    Icons.Filled.Face,
                    Icons.Outlined.Face,
                    "Posts"
                )
            )
            add(
                BottomNavigationItem(
                    uid = "dev.than0s.aluminium.core.Screen.ChatListScreen",
                    route = Screen.ChatListScreen,
                    filledIcon = Icons.AutoMirrored.Filled.Chat,
                    outlinedIcon = Icons.AutoMirrored.Outlined.Chat,
                    label = "Chat",
                )
            )
        }

        add(
            BottomNavigationItem(
                "dev.than0s.aluminium.core.Screen.SettingScreen",
                Screen.SettingScreen,
                Icons.Filled.Settings,
                Icons.Outlined.Settings,
                "Settings"
            )
        )
    }

    val isCurrentScreenHaveBottomBar = bottomNavItemsList.any { it.uid == currentRoute }

    if (isCurrentScreenHaveBottomBar) {
        NavigationBar {
            bottomNavItemsList.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.uid,
                    onClick = {
                        navController.popAndOpen(item.route)
                    },
                    icon = {
                        Icon(
                            imageVector = if (currentRoute == item.uid) {
                                item.filledIcon
                            } else {
                                item.outlinedIcon
                            },
                            contentDescription = item.label
                        )
                    },
                    label = { Text(item.label) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AluminiumTopAppBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topAppBarList = remember {
        listOf(
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.PostsScreen?userId={userId}",
                label = "Posts"
            ),
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.CommentsScreen/{postId}",
                label = "Comments"
            ),
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.ChatListScreen",
                label = "Chat"
            ),
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.SettingScreen",
                label = "Settings"
            )
        )
    }

    val isCurrentScreenHaveTopBar = topAppBarList.any { it.uid == currentRoute }

    if (isCurrentScreenHaveTopBar) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(text = topAppBarList.find { it.uid == currentRoute }?.label ?: "Aluminium")
            },
        )
    }
}

private data class BottomNavigationItem(
    val uid: String,
    val route: Screen,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val label: String
)

private data class TopAppBarItem(
    val uid: String,
    val label: String,
)