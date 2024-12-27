package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.utils.asString
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumLoadingFilledButton
import dev.than0s.aluminium.core.presentation.composable.AluminiumLottieAnimation
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
                    title = "Forget Password",
                )

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
                AluminiumLoadingFilledButton(
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
            }
        }

        AluminiumLottieAnimation(
            lottieAnimation = R.raw.forget_password_animation_2,
            modifier = Modifier.size(150.dp)
        )
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