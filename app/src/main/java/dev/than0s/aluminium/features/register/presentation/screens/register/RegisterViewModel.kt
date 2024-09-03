package dev.than0s.aluminium.features.register.presentation.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.domain.use_cases.RegistrationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegistrationUseCase) :
    ViewModel() {
    val param = mutableStateOf(RegistrationForm())
    val categoryDialogState = mutableStateOf(false)
    val batchDialogState = mutableStateOf(false)

    fun onEmailChange(email: String) {
        param.value = param.value.copy(email = email)
    }

    fun onCategoryDialogDismiss() {
        categoryDialogState.value = false
    }

    fun onCategoryClick() {
        categoryDialogState.value = true
    }

    fun onBatchDialogDismiss() {
        batchDialogState.value = false
    }

    fun onBatchClick() {
        batchDialogState.value = true
    }

    fun onCategoryChange(category: String) {
        param.value = param.value.copy(category = category)
    }

    fun onIdChange(id: String) {
        param.value = param.value.copy(id = id)
    }

    fun onFirstNameChange(firstName: String) {
        param.value = param.value.copy(firstName = firstName)
    }

    fun onMiddleNameChange(middleName: String) {
        param.value = param.value.copy(middleName = middleName)
    }

    fun onLastNameChange(lastName: String) {
        param.value = param.value.copy(lastName = lastName)
    }

    fun onBatchChange(batch: String) {
        param.value = param.value.copy(batch = batch)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            when (registerUseCase.invoke(param.value)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> TODO("show registration successfully screen")
            }
        }
    }
}