package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    supportingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder?.let {
            { Text(placeholder) }
        },

        enabled = enabled,
        isError = supportingText != null,
        shape = RoundedCornerShape(MaterialTheme.roundedCorners.default),
        supportingText = supportingText?.let {
            {
                Text(
                    text = supportingText,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
    )
}