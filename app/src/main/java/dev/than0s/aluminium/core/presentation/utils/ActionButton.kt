package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFloatingActionButton

@Composable
fun AluminiumActionButton(
    navController: NavHostController,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntry?.destination
    getDefaultFloatingActionButton(
        destination = destination,
        openScreen = navController::openScreen
    )?.let {
        PreferredFloatingActionButton(
            onClick = it.onClick,
            content = it.content
        )
    }
}

private fun getDefaultFloatingActionButton(
    destination: NavDestination?,
    openScreen: (Screen) -> Unit
): FloatingActionButtonItem? {
    return when {
        destination == null -> return null
        destination.hasRoute<Screen.HomeScreen>() -> FloatingActionButtonItem(
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            },
            onClick = {
                openScreen(Screen.PostUploadScreen)
            }
        )

        else -> null
    }
}

private data class FloatingActionButtonItem(
    val content: @Composable () -> Unit,
    val onClick: () -> Unit,
)

