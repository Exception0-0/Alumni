package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.composable.AluminiumLinearLoading
import dev.than0s.aluminium.core.presentation.composable.AluminumCircularLoading
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormat
import dev.than0s.aluminium.ui.spacing

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel(),
) {
    AboutContent(
        screenState = viewModel.screenState,
        onEvents = viewModel::onEvent
    )
}

@Composable
fun AboutContent(
    screenState: AboutState,
    onEvents: (AboutEvents) -> Unit,
) {
    if (screenState.isLoading) {
        AluminiumLinearLoading()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        ) {
            CardInfoFormat(
                title = "Role",
                info = screenState.aboutInfo.role.name,
                icon = Icons.Outlined.Email,
            )
            screenState.aboutInfo.course?.let {
                CardInfoFormat(
                    title = "Course",
                    info = it.name,
                    icon = Icons.Outlined.Phone,
                )
            }
            screenState.aboutInfo.batchFrom?.let {
                CardInfoFormat(
                    title = "Batch",
                    info = "${screenState.aboutInfo.batchFrom} - ${screenState.aboutInfo.batchTo}",
                    icon = Icons.Outlined.Star,
                )
            }
        }
    }
}