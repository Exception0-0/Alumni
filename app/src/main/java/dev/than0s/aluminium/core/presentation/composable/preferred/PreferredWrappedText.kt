package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import dev.than0s.aluminium.ui.textSize

private const val maxLine = 3

@Composable
fun PreferredWrappedText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.textSize.medium,
    fontWeight: FontWeight? = null,
) {
    var wrap by remember { mutableStateOf(true) }
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        maxLines = if (wrap) maxLine else Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.clickable {
            wrap = !wrap
        }
    )
}