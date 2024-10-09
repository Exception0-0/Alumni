package dev.than0s.aluminium.features.registration.presentation.screens.registration

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.use_cases.SubmitRegistrationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor
    (
    private val registerUseCase: SubmitRegistrationUseCase
) :
    ViewModel() {
    var param by mutableStateOf(RegistrationForm())

    fun onEmailChange(email: String) {
        param = param.copy(email = email)
    }

    fun onMobileChange(mobile: String) {
        param = param.copy(mobile = mobile)
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

    fun onBatchFromChange(from: String) {
        param = param.copy(batchFrom = from)
    }

    fun onBatchToChange(to: String) {
        param = param.copy(batchTo = to)
    }

    fun onCollegeIdChange(uri: Uri?) {
        param = param.copy(idCardImage = uri)
    }

    fun onRegisterClick(onCompleted: () -> Unit) {
        viewModelScope.launch {
            when (val result = registerUseCase.invoke(param)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Registration completed successfully")
                }
            }
            onCompleted()
        }
    }
}