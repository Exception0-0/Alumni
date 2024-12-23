package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.asString
import dev.than0s.aluminium.core.presentation.composable.ObserveAsEvents
import kotlinx.coroutines.launch

@Composable
fun SnackbarLogic(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message.asString(context),
                actionLabel = event.action?.name?.asString(context),
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
}