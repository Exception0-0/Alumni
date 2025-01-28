package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.domain.util.TextFieldLimits
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredLottieAnimation
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.textSize

@Composable
fun ForgetPasswordScreen(
    viewModel: ForgetPasswordViewModel = hiltViewModel(),
    popScreen: () -> Unit
) {
    ForgetPasswordContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        popScreen = popScreen,
    )
}

@Composable
private fun ForgetPasswordContent(
    state: ForgetPasswordState,
    onEvent: (ForgetPasswordEvents) -> Unit,
    popScreen: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredColumn(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)
        ) {
            Text(
                text = "Forget Password",
                fontSize = MaterialTheme.textSize.large,
                fontWeight = FontWeight.Bold
            )

            PreferredTextField(
                value = state.email,
                onValueChange = { newValue ->
                    onEvent(ForgetPasswordEvents.OnEmailChange(newValue))
                },
                supportingText = state.emailError?.message?.asString(),
                keyboardType = KeyboardType.Email,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = "Mail icon"
                    )
                },
                maxChar = TextFieldLimits.MAX_EMAIL,
                placeholder = "Email"
            )
            PreferredFilledButton(
                isLoading = state.isLoading,
                onClick = {
                    onEvent(
                        ForgetPasswordEvents.OnForgetPasswordClick(
                            popScreen = popScreen
                        )
                    )
                },
                enabled = !state.isLoading,
                content = {
                    Text("Forget Password")
                }
            )
            PreferredLottieAnimation(
                lottieAnimation = R.raw.forget_password_animation_2,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ForgetPasswordPreview() {
    ForgetPasswordContent(
        state = ForgetPasswordState(),
        onEvent = {},
        popScreen = {}
    )
}