package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.core.composable.AluminiumClickableText
import dev.than0s.aluminium.core.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumPasswordTextField
import dev.than0s.aluminium.core.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.composable.AluminiumTextField
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    openScreen: (Screen) -> Unit,
    popAndOpen: (Screen) -> Unit,
    restartApp: () -> Unit
) {
    SignInScreenContent(
        param = viewModel.signInParam.value,
        screenState = viewModel.signInState.value,
        onEvent = viewModel::onEvent,
        openScreen = openScreen,
        popAndOpen = popAndOpen,
        restartApp = restartApp
    )
}

@Composable
private fun SignInScreenContent(
    param: EmailAuthParam,
    screenState: SignInState,
    onEvent: (SignInEvents) -> Unit,
    openScreen: (Screen) -> Unit,
    popAndOpen: (Screen) -> Unit,
    restartApp: () -> Unit
) {

    AluminiumElevatedCard(
        modifier = Modifier
            .padding(MaterialTheme.spacing.large)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.large)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                AluminiumTitleText(
                    title = "Sign In",
                )
            }

            AluminiumTextField(
                value = param.email,
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
                value = param.password,
                onPasswordChange = { newValue ->
                    onEvent(SignInEvents.OnPasswordChange(newValue))
                },
                placeholder = "Password",
            )

            AluminiumClickableText(
                title = "Don't have an account?",
                onClick = {
                    popAndOpen(Screen.RegistrationScreen)
                }
            )

            AluminiumClickableText(
                title = "Forget Password?",
                onClick = {
                    openScreen(Screen.ForgotPasswordScreen)
                }
            )

            AluminiumLoadingElevatedButton(
                label = "Sign In",
                circularProgressIndicatorState = screenState.isLoading,
                onClick = {
                    onEvent(
                        SignInEvents.OnSignInClick(
                            onSuccess = {
                                restartApp()
                            },
                        )
                    )
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {

}