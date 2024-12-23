package dev.than0s.aluminium.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.than0s.aluminium.core.presentation.utils.AluminiumBottomNavigationBar
import dev.than0s.aluminium.core.presentation.utils.AluminiumTopAppBar
import dev.than0s.aluminium.core.presentation.utils.NavGraphHost
import dev.than0s.aluminium.core.presentation.utils.SnackbarLogic
import dev.than0s.aluminium.ui.theme.AluminiumTheme

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