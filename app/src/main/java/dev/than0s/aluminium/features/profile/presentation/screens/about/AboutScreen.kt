package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerIcons
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextHeight
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextWidth
import dev.than0s.aluminium.ui.textSize

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
        PreferredColumn {
            ListItem(
                headlineContent = {
                    Text(
                        text = "Role",
                        fontSize = MaterialTheme.textSize.medium,
                        fontWeight = FontWeight.Bold
                    )
                },
                supportingContent = {
                    Text(
                        text = screenState.aboutInfo.role.name,
                        fontSize = MaterialTheme.textSize.medium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.WorkOutline,
                        contentDescription = "role icon"
                    )
                },
            )
            screenState.aboutInfo.course?.let {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "Course",
                            fontSize = MaterialTheme.textSize.medium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    supportingContent = {
                        Text(
                            text = it.name,
                            fontSize = MaterialTheme.textSize.medium,
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.School,
                            contentDescription = "course icon"
                        )
                    },
                )
            }
            screenState.aboutInfo.batchFrom?.let {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "Batch",
                            fontSize = MaterialTheme.textSize.medium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "${screenState.aboutInfo.batchFrom} - ${screenState.aboutInfo.batchTo}",
                            fontSize = MaterialTheme.textSize.medium,
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "date icon"
                        )
                    },
                )
            }
            PreferredFilledButton(
                onClick = {
                    onEvents(AboutEvents.LoadAboutInfo)
                },
                content = {
                    Text(text = "Refresh")
                }
            )
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    PreferredColumn {
        for (i in 1..3)
            ListItem(
                headlineContent = {
                    ShimmerText(
                        height = ShimmerTextHeight.medium,
                        width = ShimmerTextWidth.small
                    )
                },
                supportingContent = {
                    ShimmerText(
                        height = ShimmerTextHeight.medium,
                        width = ShimmerTextWidth.medium
                    )
                },
                leadingContent = {
                    ShimmerIcons()
                },
            )
    }
}