package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun PreferredTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enable: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    label: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    supportingText: String? = null,
    maxChar: Int = Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= maxChar) {
                onValueChange(newValue)
            }
        },
        enabled = enable,
        label = if (label != null) {
            {
                Text(text = label)
            }
        } else null,
        placeholder = if (placeholder != null) {
            {
                Text(text = placeholder)
            }
        } else null,
        singleLine = singleLine,
        isError = supportingText != null,
        supportingText = supportingText?.let {
            @Composable {
                Text(
                    text = supportingText,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(roundedCorners.default),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        modifier = modifier.width(TextFieldDefaults.MinWidth),
    )
}

@Preview
@Composable
private fun PreferredTextFieldPreview() {
    PreferredTextField(
        value = "",
        onValueChange = {},
        placeholder = "Place",
        label = "add here",
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Mail,
                contentDescription = "Mail"
            )
        },
        supportingText = "so what",
    )
}