package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import dev.than0s.aluminium.core.composable.LoadingElevatedButton
import dev.than0s.aluminium.core.composable.PasswordTextField
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
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { callBack ->
            viewModel.onSignInClick(callBack, restartApp)
        },
        openScreen = openScreen,
        popAndOpen = popAndOpen,
    )
}

@Composable
private fun SignInScreenContent(
    param: EmailAuthParam,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: (() -> Unit) -> Unit,
    openScreen: (Screen) -> Unit,
    popAndOpen: (Screen) -> Unit,
) {
    var circularProgressIndicatorState by rememberSaveable { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .padding(MaterialTheme.spacing.large)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.large)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign In",
                    fontSize = MaterialTheme.textSize.gigantic,
                )
            }

            TextField(
                value = param.email,
                onValueChange = { newValue ->
                    onEmailChange(newValue)
                },
                label = {
                    Text(text = "Email")
                }
            )

            PasswordTextField(
                value = param.password,
                onPasswordChange = { newValue ->
                    onPasswordChange(newValue)
                },
                label = {
                    Text(text = "Password")
                }
            )

            Text(
                text = "Don't have an account?",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    popAndOpen(Screen.RegistrationScreen)
                }
            )

            Text(
                text = "Forget Password?",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    openScreen(Screen.ForgotPasswordScreen)
                }
            )

            LoadingElevatedButton(
                label = "Sign In",
                circularProgressIndicatorState = circularProgressIndicatorState,
                onClick = {
                    circularProgressIndicatorState = true
                    onSignInClick {
                        circularProgressIndicatorState = !circularProgressIndicatorState
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreenContent(EmailAuthParam(), {}, {}, {}, {}, {})
}