package dev.than0s.aluminium.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_in.SignInScreen
import dev.than0s.aluminium.features.post.presentation.screens.post_upload.PostUploadScreen
import dev.than0s.aluminium.features.splash.presentation.splash.SplashScreen
import dev.than0s.aluminium.features.registration.presentation.screens.registration.RegistrationScreen
import dev.than0s.aluminium.features.registration.presentation.screens.registration_requests.RegistrationRequestsScreen
import dev.than0s.aluminium.features.auth.presentation.screens.forget_password.ForgetPasswordScreen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_out.SignOutScreen
import dev.than0s.aluminium.features.post.presentation.screens.comments.CommentScreen
import dev.than0s.aluminium.features.post.presentation.screens.posts.PostsScreen
import dev.than0s.aluminium.features.post.presentation.screens.posts.SpecificPostsScreen
import dev.than0s.aluminium.features.profile.presentation.screens.profile.ProfileScreen
import dev.than0s.aluminium.features.profile.presentation.screens.settings.SettingScreen
import dev.than0s.aluminium.ui.theme.AluminiumTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AluminiumTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        AluminiumTopAppBar(navController)
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = viewModel.snackbarHostState
                        )
                    },
                    bottomBar = {
                        AluminiumBottomNavigationBar(navController)
                    }
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
        composable<Screen.SpecificPostsScreen> {
            SpecificPostsScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.ProfileScreen> {
            ProfileScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.CommentsScreen> {
            CommentScreen()
        }
    }
}

private fun NavHostController.openScreen(screen: Screen) {
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
private fun AluminiumBottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItemsList = remember {
        listOf(
            BottomNavigationItem(
                "dev.than0s.aluminium.core.Screen.PostsScreen?userId={userId}",
                Screen.PostsScreen(),
                Icons.Filled.Face,
                Icons.Outlined.Face,
                "Posts"
            ),
            BottomNavigationItem(
                "dev.than0s.aluminium.core.Screen.SettingScreen",
                Screen.SettingScreen,
                Icons.Filled.AccountCircle,
                Icons.Outlined.AccountCircle,
                "Profile"
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
private fun AluminiumTopAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topAppBarList = remember {
        listOf(
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.CommentsScreen",
                label = "Comments"
            ),
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
            }
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