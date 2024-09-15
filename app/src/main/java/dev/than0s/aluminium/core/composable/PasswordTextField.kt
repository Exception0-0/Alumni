package dev.than0s.aluminium.core.composable

import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordTextField(
    value: String,
    onPasswordChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null
) {
    var passwordVisibilityState by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = { newValue ->
            onPasswordChange(newValue)
        },
        label = label,
        visualTransformation = if (passwordVisibilityState) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibilityState = !passwordVisibilityState
            }) {
//                if (passwordVisibilityState) {
//                    Icon(
//                        imageVector = Icons.Default.VisibilityOff,
//                        contentDescription = "Visibility"
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.Default.Visibility,
//                        contentDescription = "Visibility"
//                    )
//                }
            }
        },
    )
}