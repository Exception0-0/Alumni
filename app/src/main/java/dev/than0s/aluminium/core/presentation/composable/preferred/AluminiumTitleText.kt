package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import dev.than0s.aluminium.ui.textSize

@Composable
fun AluminiumTitleText(
    title: String,
    fontSize: TextUnit = MaterialTheme.textSize.gigantic,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        fontWeight = FontWeight.W500,
        fontSize = fontSize,
        modifier = modifier
    )
}

@Composable
fun AluminiumGroupTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(16.dp)
    )
}

@Preview
@Composable
private fun AluminiumTitleTextPreview() {
    AluminiumTitleText(
        "Title"
    )
}