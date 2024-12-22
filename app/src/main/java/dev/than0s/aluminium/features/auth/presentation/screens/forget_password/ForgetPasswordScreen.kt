package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.asString
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumTextField
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.ui.spacing

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
                    title = "Forget Password",
                )
            }

            AluminiumTextField(
                value = state.email,
                onValueChange = { newValue ->
                    onEvent(ForgetPasswordEvents.OnEmailChange(newValue))
                },
                supportingText = state.emailError?.message?.asString(),

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = "Mail icon"
                    )
                },
                placeholder = "Email"
            )

            AluminiumLoadingElevatedButton(
                label = "Forget Password",
                circularProgressIndicatorState = state.isLoading,
                onClick = {
                    onEvent(
                        ForgetPasswordEvents.OnForgetPasswordClick(
                            onSuccess = popScreen
                        )
                    )
                }
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