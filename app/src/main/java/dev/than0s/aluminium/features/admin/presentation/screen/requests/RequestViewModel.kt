package dev.than0s.aluminium.features.admin.presentation.screen.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.admin.domain.data_class.RequestForm
import dev.than0s.aluminium.features.admin.domain.use_cases.AcceptedUseCase
import dev.than0s.aluminium.features.admin.domain.use_cases.RejectedUserCase
import dev.than0s.aluminium.features.admin.domain.use_cases.RequestsListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestsUseCase: RequestsListUseCase,
    private val acceptedUseCase: AcceptedUseCase,
    private val rejectedUserCase: RejectedUserCase
) :
    ViewModel() {
    var requestsList: Flow<List<RequestForm>> = emptyFlow()

    init {
        viewModelScope.launch {
            when (val result = requestsUseCase.invoke(Unit)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> requestsList = result.value
            }
        }
    }

    fun onAcceptClick(form: RequestForm) {
        viewModelScope.launch {
            when (val result = acceptedUseCase.invoke(form)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> println("AcceptedSuccess")
            }
        }
    }

    fun onRejectedClick(form: RequestForm) {
        viewModelScope.launch {
            when (val result = rejectedUserCase.invoke(form)) {
                is Either.Left -> TODO("show error message")
                is Either.Right -> println("Rejected Success")
            }
        }
    }
}