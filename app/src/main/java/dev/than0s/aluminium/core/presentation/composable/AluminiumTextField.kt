package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import dev.than0s.aluminium.ui.roundCorners

@Composable
fun AluminiumTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    supportingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        enabled = enable,
        label = {
            Text(text = placeholder)
        },
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
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun AluminiumTextFieldPreview() {
    AluminiumTextField(
        value = "",
        onValueChange = {},
        placeholder = "Place",
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Mail,
                contentDescription = "Mail"
            )
        },
        supportingText = "so what",
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
        }
    )
}