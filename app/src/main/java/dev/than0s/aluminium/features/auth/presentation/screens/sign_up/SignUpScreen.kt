package dev.than0s.aluminium.features.auth.presentation.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.mydiary.core.data_class.EmailAuthParam
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    SignUpScreenContent(
        param = viewModel.signUpParam.value,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignUpClick = viewModel::onSignInClick
    )
}

@Composable
private fun SignUpScreenContent(
    param: EmailAuthParam,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign Up",
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

            ElevatedButton(
                onClick = {
                    onSignUpClick()
                }
            ) {
                Text(text = "Sign Up")
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreenContent(EmailAuthParam(), {}, {}, {})
}