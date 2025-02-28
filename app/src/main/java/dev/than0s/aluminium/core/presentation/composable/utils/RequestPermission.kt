package dev.than0s.aluminium.core.presentation.composable.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission() {
    val permission = rememberPermissionState(
        permission = android.Manifest.permission.POST_NOTIFICATIONS
    )
    if (!permission.status.isGranted) {
        LaunchedEffect(Unit) {
            permission.launchPermissionRequest()
        }
    }
}