package dev.than0s.aluminium.features.registration.presentation.screens.registration_requests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.use_cases.AcceptRegistrationRequest
import dev.than0s.aluminium.features.registration.domain.use_cases.RegistrationRequestListUseCase
import dev.than0s.aluminium.features.registration.domain.use_cases.RejectRegistrationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestsUseCase: RegistrationRequestListUseCase,
    private val acceptedUseCase: AcceptRegistrationRequest,
    private val rejectedUserCase: RejectRegistrationRequest
) :
    ViewModel() {
    var requestsList: Flow<List<RegistrationForm>> by mutableStateOf(emptyFlow())

    init {
        viewModelScope.launch {
            when (val result = requestsUseCase.invoke(Unit)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> requestsList = result.value
            }
        }
    }

    fun onAcceptClick(form: RegistrationForm, onCompleted: () -> Unit) {
        viewModelScope.launch {
            when (val result = acceptedUseCase.invoke(form)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Request accepted successfully")
                }
            }
            onCompleted()
        }
    }

    fun onRejectedClick(form: RegistrationForm, onCompleted: () -> Unit) {
        viewModelScope.launch {
            when (val result = rejectedUserCase.invoke(form)) {
                is Either.Left -> {
                    SnackbarController.showSnackbar(result.value.message)
                }

                is Either.Right -> {
                    SnackbarController.showSnackbar("Request rejected successfully")
                }
            }
            onCompleted()
        }
    }
}