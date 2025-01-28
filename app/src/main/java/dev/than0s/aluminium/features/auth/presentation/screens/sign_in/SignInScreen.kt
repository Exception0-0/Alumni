package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

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
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredClickableText
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredLottieAnimation
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredPasswordTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.textSize

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
    restartApp: () -> Unit
) {
    SignInScreenContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent,
        openScreen = openScreen,
        restartApp = restartApp
    )
}

@Composable
private fun SignInScreenContent(
    screenState: SignInState,
    onEvent: (SignInEvents) -> Unit,
    openScreen: (Screen) -> Unit,
    restartApp: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PreferredColumn(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center),
        ) {
            Text(
                text = "Sign In",
                fontSize = MaterialTheme.textSize.large,
                fontWeight = FontWeight.Bold
            )

            PreferredTextField(
                value = screenState.email,
                enable = !screenState.isLoading,
                keyboardType = KeyboardType.Email,
                supportingText = screenState.emailError?.message?.asString(),
                onValueChange = { newValue ->
                    onEvent(SignInEvents.OnEmailChanged(newValue))
                },
                maxChar = TextFieldLimits.MAX_EMAIL,
                placeholder = "Email",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = "Mail Icon"
                    )
                }
            )

            PreferredPasswordTextField(
                value = screenState.password,
                passwordVisibilityState = screenState.isPasswordVisible,
                onPasswordVisibilityChange = {
                    onEvent(SignInEvents.OnPasswordVisibilityChange)
                },
                enable = !screenState.isLoading,
                maxChar = TextFieldLimits.MAX_PASSWORD,
                supportingText = screenState.passwordError?.message?.asString(),
                onPasswordChange = { newValue ->
                    onEvent(SignInEvents.OnPasswordChange(newValue))
                },
                placeholder = "Password",
            )

            PreferredClickableText(
                text = "Don't have an account?",
                enabled = !screenState.isLoading,
                onClick = {
                    openScreen(Screen.RegistrationScreen)
                }
            )

            PreferredClickableText(
                text = "Forget Password?",
                enabled = !screenState.isLoading,
                onClick = {
                    openScreen(Screen.ForgotPasswordScreen)
                }
            )

            PreferredFilledButton(
                isLoading = screenState.isLoading,
                onClick = {
                    onEvent(
                        SignInEvents.OnSignInClick(
                            restartApp = restartApp,
                        )
                    )
                },
                enabled = !screenState.isLoading,
                content = {
                    Text("Sign In")
                }
            )

            PreferredLottieAnimation(
                lottieAnimation = R.raw.authentication_animation,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreenContent(
        screenState = SignInState(),
        onEvent = {},
        openScreen = {},
        restartApp = {}
    )
}