package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.textSize

@Composable
fun PreferredClickableText(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textAlign: TextAlign? = TextAlign.Center
) {
    Text(
        text = text,
        textDecoration = TextDecoration.Underline,
        fontSize = MaterialTheme.textSize.medium,
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary
        ),
        textAlign = textAlign,
        modifier = Modifier
            .clickable(enabled = enabled) {
                onClick()
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreferredClickableTextPreview() {
    PreferredClickableText(
        text = "Click on me",
        onClick = {}
    )
}