package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.textSize

@Composable
fun AluminiumDescriptionText(
    description: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = description,
        fontSize = MaterialTheme.textSize.medium,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun AluminiumDescriptionTextPreview() {
    AluminiumDescriptionText(
        description = "Hello there I want to tell you something I never tell any one"
    )
}