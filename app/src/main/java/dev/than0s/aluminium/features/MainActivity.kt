package dev.than0s.aluminium.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import dev.than0s.aluminium.ui.textSize
import dev.than0s.aluminium.ui.theme.AluminiumTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: ViewModelMainActivity

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
                TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            val connection by connectivityState()
            val isConnected = connection === ConnectionState.Available
            val a = 16.dp

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
                            AluminiumBottomNavigationBar(navController)
                            AnimatedVisibility(
                                visible = !isConnected,
                                enter = expandVertically(),
                                exit = shrinkVertically()
                            ) {
                                Text(
                                    text = stringResource(R.string.network_error),
                                    fontSize = MaterialTheme.textSize.medium,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.errorContainer)
                                        .fillMaxWidth()
                                )
                            }
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