package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.than0s.aluminium.ui.roundCorners

@Composable
fun AluminiumPasswordTextField(
    value: String,
    onPasswordChange: (String) -> Unit,
    placeholder: String,
    enable: Boolean = true,
    supportingText: String? = null,
    modifier: Modifier = Modifier,
) {
    var passwordVisibilityState by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = { newValue ->
            onPasswordChange(newValue)
        },
        label = {
            Text(text = placeholder)
        },
        enabled = enable,
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
        visualTransformation = if (passwordVisibilityState) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibilityState = !passwordVisibilityState
            }) {
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