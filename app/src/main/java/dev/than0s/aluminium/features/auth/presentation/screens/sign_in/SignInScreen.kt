package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.AluminiumClickableText
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingFilledButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumLottieAnimation
import dev.than0s.aluminium.core.presentation.composable.AluminiumPasswordTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.ui.spacing

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

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(MaterialTheme.spacing.large)
    ) {
        AluminiumElevatedCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.spacing.large)
            ) {
                AluminiumTitleText(
                    title = "Sign In"
                )

                AluminiumTextField(
                    value = screenState.email,
                    enable = !screenState.isLoading,
                    keyboardType = KeyboardType.Email,
                    supportingText = screenState.emailError?.message?.asString(),
                    onValueChange = { newValue ->
                        onEvent(SignInEvents.OnEmailChanged(newValue))
                    },
                    placeholder = "Email",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Mail,
                            contentDescription = "Mail Icon"
                        )
                    }
                )

                AluminiumPasswordTextField(
                    value = screenState.password,
                    enable = !screenState.isLoading,
                    supportingText = screenState.passwordError?.message?.asString(),
                    onPasswordChange = { newValue ->
                        onEvent(SignInEvents.OnPasswordChange(newValue))
                    },
                    placeholder = "Password",
                )

                AluminiumClickableText(
                    title = "Don't have an account?",
                    onClick = {
                        openScreen(Screen.RegistrationScreen)
                    }
                )

                AluminiumClickableText(
                    title = "Forget Password?",
                    onClick = {
                        openScreen(Screen.ForgotPasswordScreen)
                    }
                )

                AluminiumLoadingFilledButton(
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
            }
        }
        AluminiumLottieAnimation(
            lottieAnimation = R.raw.authentication_animation,
            modifier = Modifier.size(150.dp)
        )
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