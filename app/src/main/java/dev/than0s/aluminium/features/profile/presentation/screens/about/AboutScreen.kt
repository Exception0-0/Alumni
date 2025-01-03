package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormat
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormatShimmer
import dev.than0s.aluminium.ui.padding

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
        LoadingShimmerEffect()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.large),
        ) {
            CardInfoFormat(
                title = "Role",
                info = screenState.aboutInfo.role.name,
                icon = Icons.Outlined.WorkOutline,
            )
            screenState.aboutInfo.course?.let {
                CardInfoFormat(
                    title = "Course",
                    info = it.name,
                    icon = Icons.Outlined.School,
                )
            }
            screenState.aboutInfo.batchFrom?.let {
                CardInfoFormat(
                    title = "Batch",
                    info = "${screenState.aboutInfo.batchFrom} - ${screenState.aboutInfo.batchTo}",
                    icon = Icons.Outlined.DateRange,
                )
            }
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.large)
    ) {
        CardInfoFormatShimmer()
        CardInfoFormatShimmer()
        CardInfoFormatShimmer()
    }
}