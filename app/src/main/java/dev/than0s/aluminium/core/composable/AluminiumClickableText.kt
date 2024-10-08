package dev.than0s.aluminium.core.composable

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AluminiumClickableText(
    title: String,
    onClick: () -> Unit
) {
    Text(
        text = title,
        textDecoration = TextDecoration.Underline,
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