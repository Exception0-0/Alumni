package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.ui.spacing

@Composable
fun AboutTabContent(
    aboutInfo: AboutInfo,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        InfoFormat(
            title = "Role",
            info = aboutInfo.role.name,
            icon = Icons.Outlined.Email,
        )
        aboutInfo.course?.let {
            InfoFormat(
                title = "Course",
                info = it.name,
                icon = Icons.Outlined.Phone,
            )
        }
        aboutInfo.batchFrom?.let {
            InfoFormat(
                title = "Batch",
                info = "${aboutInfo.batchFrom} - ${aboutInfo.batchTo}",
                icon = Icons.Outlined.Star,
            )
        }
    }
}