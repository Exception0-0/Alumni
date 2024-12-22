package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.textSize

@Composable
fun AluminiumClickableText(
    title: String,
    onClick: () -> Unit
) {
    Text(
        text = title,
        textDecoration = TextDecoration.Underline,
        fontSize = MaterialTheme.textSize.large,
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AluminiumClickableTextPreview() {
    AluminiumClickableText(
        title = "Click on me",
        onClick = {}
    )
}