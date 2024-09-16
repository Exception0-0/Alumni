package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.Screen
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    openScreen: (String) -> Unit,
    popAndOpen: (String) -> Unit,
    restartApp: () -> Unit
) {
    SignInScreenContent(
        param = viewModel.signInParam.value,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = {
            viewModel.onSignInClick(restartApp)
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
    onSignInClick: () -> Unit,
    openScreen: (String) -> Unit,
    popAndOpen: (String) -> Unit,
) {
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
                placeholder = {
                    Text(text = "Email")
                }
            )

            TextField(
                value = param.password,
                onValueChange = { newValue ->
                    onPasswordChange(newValue)
                },
                placeholder = {
                    Text(text = "Password")
                }
            )

            Text(
                text = "Don't have an account?",
                modifier = Modifier.clickable {
                    popAndOpen(Screen.RegistrationScreen.route)
                }
            )

            Text(
                text = "Forget Password?",
                modifier = Modifier.clickable {
                    openScreen(Screen.ForgotPasswordScreen.route)
                }
            )

            ElevatedButton(
                onClick = {
                    onSignInClick()
                }
            ) {
                Text(text = "Sign In")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreenContent(EmailAuthParam(), {}, {}, {}, {}, {})
}