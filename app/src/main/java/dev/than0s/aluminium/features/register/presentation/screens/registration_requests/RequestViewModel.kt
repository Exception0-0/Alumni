package dev.than0s.aluminium.features.register.presentation.screens.registration_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.domain.use_cases.AcceptedUseCase
import dev.than0s.aluminium.features.register.domain.use_cases.RejectedUserCase
import dev.than0s.aluminium.features.register.domain.use_cases.RequestsListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestsUseCase: RequestsListUseCase,
    private val acceptedUseCase: AcceptedUseCase,
    private val rejectedUserCase: RejectedUserCase
) :
    ViewModel() {
    lateinit var requestsList: Flow<List<RegistrationForm>>

    init {
        viewModelScope.launch {
            when (val result = requestsUseCase.invoke(Unit)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> requestsList = result.value
            }
        }
    }

    fun onAcceptClick(form: RegistrationForm) {
        viewModelScope.launch {
            when (val result = acceptedUseCase.invoke(form)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> println("AcceptedSuccess")
            }
        }
    }

    fun onRejectedClick(form: RegistrationForm) {
        viewModelScope.launch {
            when (val result = rejectedUserCase.invoke(form)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> println("Rejected Success")
            }
        }
    }
}