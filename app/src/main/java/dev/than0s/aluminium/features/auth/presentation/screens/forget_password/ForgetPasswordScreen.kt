package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

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
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun ForgetPasswordScreen(
    viewModel: ForgetPasswordViewModel = hiltViewModel(),
    popScreen: () -> Unit
) {
    ForgetPasswordContent(
        email = viewModel.email,
        onForgetPasswordClick = {
            viewModel.onForgetPasswordClick(popScreen)
        },
        onEmailChange = viewModel::onEmailChange
    )
}

@Composable
private fun ForgetPasswordContent(
    email: String,
    onForgetPasswordClick: () -> Unit,
    onEmailChange: (String) -> Unit
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
                    text = "Forget Password",
                    fontSize = MaterialTheme.textSize.gigantic,
                )
            }

            TextField(
                value = email,
                onValueChange = { newValue ->
                    onEmailChange(newValue)
                },
                placeholder = {
                    Text(text = "Email")
                }
            )

            ElevatedButton(
                onClick = {
                    onForgetPasswordClick()
                }
            ) {
                Text(text = "Forget Password")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ForgetPasswordPreview() {
    ForgetPasswordContent("", {}, {})
}