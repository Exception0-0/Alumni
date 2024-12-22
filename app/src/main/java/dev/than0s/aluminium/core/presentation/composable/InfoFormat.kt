package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize


@Composable
fun CardInfoFormat(
    title: String,
    info: String,
    icon: ImageVector,
) {
    AluminiumElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                AluminiumTitleText(
                    title = title,
                    fontSize = MaterialTheme.textSize.large
                )

                AluminiumDescriptionText(
                    description = info,
                )
            }
        }
    }
}