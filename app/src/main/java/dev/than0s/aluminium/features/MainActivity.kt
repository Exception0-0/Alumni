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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.than0s.aluminium.DemoScreen
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.profile.presentation.screens.profile.ProfileScreen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_in.SignInScreen
import dev.than0s.aluminium.features.post.presentation.screens.post_upload.PostUploadScreen
import dev.than0s.aluminium.features.splash.presentation.splash.SplashScreen
import dev.than0s.aluminium.features.registration.presentation.screens.registration.RegistrationScreen
import dev.than0s.aluminium.features.admin.presentation.screen.requests.RegistrationRequestsScreen
import dev.than0s.aluminium.features.auth.presentation.screens.forget_password.ForgetPasswordScreen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_out.SignOutScreen
import dev.than0s.aluminium.features.post.presentation.screens.all_posts.AllPostsScreen
import dev.than0s.aluminium.features.post.presentation.screens.my_posts.MyPostsScreen
import dev.than0s.aluminium.ui.theme.AluminiumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AluminiumTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController
                        )
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
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                popAndOpen = navController::popAndOpen
            )
        }
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(
                openScreen = navController::openScreen,
                popAndOpen = navController::popAndOpen,
                restartApp = navController::restartApp
            )
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(
                popAndOpen = navController::popAndOpen
            )
        }
        composable(route = Screen.DemoScreen.route) {
            DemoScreen()
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(
                openScreen = navController::openScreen,
            )
        }
        composable(route = Screen.RegistrationRequestsScreen.route) {
            RegistrationRequestsScreen()
        }
        composable(route = Screen.PostUploadScreen.route) {
            PostUploadScreen(
                popScreen = navController::popScreen
            )
        }
        composable(route = Screen.SignOutScreen.route) {
            SignOutScreen(
                restartApp = navController::restartApp
            )
        }
        composable(route = Screen.ForgotPasswordScreen.route) {
            ForgetPasswordScreen(
                popScreen = navController::popScreen
            )
        }
        composable(route = Screen.PostUploadScreen.route) {
            PostUploadScreen(
                popScreen = navController::popScreen

            )
        }
        composable(route = Screen.MyPostsScreen.route) {
            MyPostsScreen(
                openScreen = navController::openScreen
            )
        }
        composable(route = Screen.AllPostScreen.route) {
            AllPostsScreen()
        }
    }
}

private fun NavHostController.openScreen(screen: String) {
    navigate(screen)
}

private fun NavHostController.popScreen() {
    popBackStack()
}

private fun NavHostController.popAndOpen(screen: String) {
    popBackStack()
    navigate(screen)
}

private fun NavHostController.restartApp() {
    navigate(Screen.SplashScreen.route) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
}

private data class BottomNavigationItem(
    val route: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val label: String
)

private val BottomNavItems = listOf(
    BottomNavigationItem(
        Screen.AllPostScreen.route,
        Icons.Filled.Face,
        Icons.Outlined.Face,
        "Posts"
    ),
    BottomNavigationItem(
        Screen.ProfileScreen.route,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        "Profile"
    )
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.popAndOpen(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) {
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
