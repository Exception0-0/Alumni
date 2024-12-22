package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.roundCorners

@Composable
fun AluminiumTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
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
        isError = supportingText != null,
        supportingText = supportingText?.let {
            @Composable {
                Text(text = supportingText)
            }
        },
        shape = RoundedCornerShape(MaterialTheme.roundCorners.default),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        modifier = modifier,

        )
}

@Preview
@Composable
fun AluminiumTextFieldPreview() {
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
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
        }
    )
}