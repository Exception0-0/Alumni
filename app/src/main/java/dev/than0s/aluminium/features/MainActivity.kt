package dev.than0s.aluminium.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.than0s.aluminium.Screen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_in.SignInScreen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_up.SignUpScreen
import dev.than0s.aluminium.features.auth.presentation.screens.splash.SplashScreen
import dev.than0s.aluminium.ui.theme.AluminiumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AluminiumTheme {
                Scaffold { paddingValue ->
                    NavGraphHost(
                        modifier = Modifier.padding(paddingValue)
                    )
                }
            }
        }
    }
}

@Composable
private fun NavGraphHost(modifier: Modifier) {
    val navController = rememberNavController()
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
                restartApp = navController::restartApp
            )
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(
                openScreen = navController::openScreen,
                restartApp = navController::restartApp
            )
        }
    }
}

private fun NavHostController.openScreen(screen: String) {
    navigate(screen)
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
