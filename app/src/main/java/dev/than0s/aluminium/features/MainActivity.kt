package dev.than0s.aluminium.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.DYNAMIC_THEME
import dev.than0s.aluminium.core.presentation.ui.PURE_BLACK
import dev.than0s.aluminium.core.presentation.ui.getCurrentColorTheme
import dev.than0s.aluminium.core.presentation.utils.AluminiumActionButton
import dev.than0s.aluminium.core.presentation.utils.AluminiumBottomNavigationBar
import dev.than0s.aluminium.core.presentation.utils.AluminiumTopAppBar
import dev.than0s.aluminium.core.presentation.utils.ConnectionState
import dev.than0s.aluminium.core.presentation.utils.NavGraphHost
import dev.than0s.aluminium.core.presentation.utils.SnackbarLogic
import dev.than0s.aluminium.core.presentation.utils.connectivityState
import dev.than0s.aluminium.ui.theme.AluminiumTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = when (getCurrentColorTheme()) {
                ColorTheme.System -> isSystemInDarkTheme()
                ColorTheme.Dark -> true
                ColorTheme.Light -> false
            }
            val pureBlack by rememberBooleanPreference(
                keyName = PURE_BLACK,
                defaultValue = false,
                initialValue = false
            )
            val isDynamicTheme by rememberBooleanPreference(
                keyName = DYNAMIC_THEME,
                initialValue = false,
                defaultValue = false,
            )
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scrollBehavior =
                TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
            val connection by connectivityState()
            val isConnected = connection === ConnectionState.Available

            AluminiumTheme(
                darkTheme = darkTheme,
                dynamicColor = isDynamicTheme,
                pureBlack = pureBlack,
            ) {

                SnackbarLogic(
                    snackbarHostState = snackbarHostState
                )

                Scaffold(
                    contentWindowInsets = WindowInsets(0.dp),
                    topBar = {
                        AluminiumTopAppBar(
                            navController = navController,
                            scrollBehavior = scrollBehavior
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    bottomBar = {
                        PreferredColumn(
                            verticalArrangement = Arrangement.Top
                        ) {
                            if (!isConnected) {
                                ListItem(
                                    leadingContent = {
                                        Icon(
                                            imageVector = Icons.Outlined.Warning,
                                            contentDescription = "warning"
                                        )
                                    },
                                    headlineContent = {
                                        Text(
                                            text = stringResource(R.string.network_error),
                                        )
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        headlineColor = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                )
                            }
                            AluminiumBottomNavigationBar(navController)
                        }
                    },
                    floatingActionButton = {
                        AluminiumActionButton(navController)
                    },
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
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