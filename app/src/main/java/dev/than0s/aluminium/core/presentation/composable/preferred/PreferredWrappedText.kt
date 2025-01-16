package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import dev.than0s.aluminium.ui.textSize

private const val maxTextLength = 100

@Composable
fun PreferredWrappedText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.textSize.medium,
    fontWeight: FontWeight? = null,
) {
    var isWrapped by remember { mutableStateOf(text.length > maxTextLength) }
    if (isWrapped) {
        PreferredColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.clickable {
                isWrapped = false
            }
        ) {
            Text(
                text = buildAnnotatedString {
                    append(text.substring(0, maxTextLength))
                    pushStyle(SpanStyle(color = MaterialTheme.colorScheme.primary,fontWeight = FontWeight.Bold))
                    append(" ...tap to read more")
                    pop()
                },
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        }
    } else {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = modifier.clickable {
                isWrapped = true
            }
        )
    }
}