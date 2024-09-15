package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.auth.domain.use_cases.ForgetPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(private val useCase: ForgetPasswordUseCase) :
    ViewModel() {
    var email by mutableStateOf("")

    fun onForgetPasswordClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (useCase.invoke(email)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> {
                    onSuccess()
                    TODO("show success message")
                }
            }
        }
    }

    fun onEmailChange(value: String) {
        email = value
    }
}