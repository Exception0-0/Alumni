package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

private var customAppBar by mutableStateOf<TopAppBarItem?>(null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumTopAppBar(
    navController: NavHostController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val appBar = customAppBar ?: getDefaultTopAppBar(currentBackStackEntry)

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
        actions = appBar.actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

private fun removeCustomAppBar() {
    if (customAppBar != null) {
        customAppBar = null
    }
}

fun addCustomAppBar(appBar: TopAppBarItem) {
    customAppBar = appBar
}

private fun getDefaultTopAppBar(
    currentBackStackEntry: NavBackStackEntry?
): TopAppBarItem {
    val className = getClassNameFromNavGraph(currentBackStackEntry?.destination)
    return TopAppBarItem(
        title = {
            Text(
                text = getScreenName(className) ?: "Than0s"
            )
        }
    )
}

data class TopAppBarItem(
    val title: @Composable () -> Unit = {},
    val actions: @Composable (RowScope.() -> Unit) = {},
)