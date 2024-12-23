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
import dev.than0s.aluminium.core.presentation.utils.AluminiumBottomNavigationBar
import dev.than0s.aluminium.core.presentation.utils.AluminiumTopAppBar
import dev.than0s.aluminium.core.presentation.utils.NavGraphHost
import dev.than0s.aluminium.core.presentation.utils.SnackbarLogic
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

                SnackbarLogic(
                    snackbarHostState = snackbarHostState
                )

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