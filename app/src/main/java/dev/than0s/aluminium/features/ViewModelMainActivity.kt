package dev.than0s.aluminium.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.than0s.aluminium.core.LAST_SEEN_UPDATE_SEC
import dev.than0s.aluminium.core.domain.use_case.UseCaseUpdateLastSeen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelMainActivity @Inject constructor(
    private val useCaseUpdateLastSeen: UseCaseUpdateLastSeen
) : ViewModel() {
    init {
        updateLastSeen()
    }

    private fun updateLastSeen() {
        viewModelScope.launch {
            while (true) {
                println("last_seen")
                useCaseUpdateLastSeen()
                delay(1000L * LAST_SEEN_UPDATE_SEC)
            }
        }
    }
}