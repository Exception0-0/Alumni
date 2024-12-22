package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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

@Preview
@Composable
private fun AluminiumTitleTextPreview() {
    AluminiumTitleText(
        "Title"
    )
}