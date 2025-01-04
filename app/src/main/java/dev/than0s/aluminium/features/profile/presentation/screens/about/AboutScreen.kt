package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerIcons
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextHeight
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextWidth
import dev.than0s.aluminium.ui.padding
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
        PreferredColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
        ) {
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
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
    ) {
        for (i in 1..3) {
            ListItem(
                headlineContent = {
                    ShimmerText(
                        height = ShimmerTextHeight.medium,
                        width = ShimmerTextWidth.small
                    )
                },
                supportingContent = {
                    PreferredColumn(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        VerticalDivider(
                            thickness = 0.dp,
                            modifier = Modifier.height(MaterialTheme.padding.extraSmall)
                        )
                        ShimmerText(
                            height = ShimmerTextHeight.medium,
                            width = ShimmerTextWidth.medium
                        )
                    }
                },
                leadingContent = {
                    ShimmerIcons()
                },
                modifier = Modifier.shimmer()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    LoadingShimmerEffect()
}