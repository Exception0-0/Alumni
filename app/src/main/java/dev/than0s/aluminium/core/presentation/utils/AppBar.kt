package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

private var customAppBar by mutableStateOf<TopAppBarItem?>(null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumTopAppBar(
    navController: NavHostController
) {
    val appBar = customAppBar ?: getDefaultTopAppBar(navController)

    TopAppBar(
        title = appBar.title,
        navigationIcon = {
            IconButton(onClick = {
                navController.popScreen()
                removeCustomAppBar()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back button"
                )
            }
        },
        actions = appBar.actions
    )
}

private fun removeCustomAppBar() {
    customAppBar = null
}

fun setCustomAppBar(appBar: TopAppBarItem) {
    customAppBar = appBar
}

private fun getDefaultTopAppBar(
    navController: NavHostController
): TopAppBarItem {
    val navBackStackEntry = navController.currentBackStackEntry
    val currentRoute = navBackStackEntry?.destination?.route
    return TopAppBarItem(
        title = {
            Text("Aluminium")
        }
    )
}

data class TopAppBarItem(
    val title: @Composable () -> Unit = {},
    val actions: @Composable (RowScope.() -> Unit) = {},
)