package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.auth.domain.data_class.Email
import dev.than0s.aluminium.features.auth.domain.use_cases.ForgetPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val useCase: ForgetPasswordUseCase) :
    ViewModel() {
    var param by mutableStateOf(Email())
    var state by mutableStateOf(ForgetPasswordState())

    private fun onForgetPasswordClick(
        onSuccess: () -> Unit,
        onComplete: () -> Unit,
    ) {
//        viewModelScope.launch {
//            when (val result = useCase.invoke(param)) {
//                is Either.Left -> {
//                    SnackbarController.showSnackbar(result.value.message)
//                }
//
//                is Either.Right -> {
//                    SnackbarController.showSnackbar("successfully forget password")
//                    onSuccess()
//                }
//            }
//            onComplete()
//        }
    }

    private fun onEmailChange(value: String) {
        param = param.copy(email = value)
    }

    fun onEvent(
        event: ForgetPasswordEvents
    ) {
        when (event) {
            is ForgetPasswordEvents.onForgetPasswordClick -> {
                onForgetPasswordClick(
                    onSuccess = event.onSuccess,
                    onComplete = event.onComplete
                )
            }

            is ForgetPasswordEvents.onEmailChange -> {
                onEmailChange(
                    value = event.email
                )
            }
        }
    }
}