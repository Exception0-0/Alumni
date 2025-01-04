package dev.than0s.aluminium.core.presentation.composable.shimmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.ui.padding

@Composable
fun ShimmerListItem() {
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
        trailingContent = {
            ShimmerIcons()
        },
        modifier = Modifier.shimmer()
    )
}