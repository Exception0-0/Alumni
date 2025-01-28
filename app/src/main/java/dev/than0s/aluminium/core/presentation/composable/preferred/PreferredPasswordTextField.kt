package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PreferredPasswordTextField(
    value: String,
    passwordVisibilityState: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onPasswordChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxChar: Int = Int.MAX_VALUE,
    enable: Boolean = true,
    singleLine: Boolean = true,
    supportingText: String? = null,
) {
    PreferredTextField(
        value = value,
        onValueChange = { newValue ->
            onPasswordChange(newValue)
        },
        placeholder = placeholder,
        singleLine = singleLine,
        keyboardType = KeyboardType.Password,
        enable = enable,
        supportingText = supportingText,
        maxChar = maxChar,
        visualTransformation = if (passwordVisibilityState)
            VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityChange) {
                if (passwordVisibilityState) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Visibility"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "Visibility"
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Password,
                contentDescription = "password"
            )
        },
        modifier = modifier
    )
}