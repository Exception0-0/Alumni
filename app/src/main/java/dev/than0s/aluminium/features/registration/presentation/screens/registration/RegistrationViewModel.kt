package dev.than0s.aluminium.features.registration.presentation.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.use_cases.SubmitRegistrationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val registerUseCase: SubmitRegistrationUseCase) :
    ViewModel() {
    var param by mutableStateOf(RegistrationForm())
    val categoryDialogState = mutableStateOf(false)
    val batchDialogState = mutableStateOf(false)

    fun onEmailChange(email: String) {
        param = param.copy(email = email)
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
        param = param.copy(category = category)
    }

    fun onRollNoChange(rollNo: String) {
        param = param.copy(rollNo = rollNo)
    }

    fun onFirstNameChange(firstName: String) {
        param = param.copy(firstName = firstName)
    }

    fun onMiddleNameChange(middleName: String) {
        param = param.copy(middleName = middleName)
    }

    fun onLastNameChange(lastName: String) {
        param = param.copy(lastName = lastName)
    }

    fun onBatchChange(batch: String) {
        param = param.copy(batch = batch)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            when (registerUseCase.invoke(param)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> TODO("show registration successfully screen")
            }
        }
    }
}