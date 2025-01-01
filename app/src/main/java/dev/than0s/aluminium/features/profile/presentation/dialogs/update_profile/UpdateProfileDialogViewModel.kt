package dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.currentUserId
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.profile.domain.use_cases.SetProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileDialogViewModel @Inject constructor(
    private val updateProfileUseCase: SetProfileUseCase,
    private val getUserUserCase: GetUserUseCase
) : ViewModel() {
    var screenState by mutableStateOf(UpdateProfileDialogState())

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = getUserUserCase(currentUserId!!)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        userProfile = result.data!!
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onUpdateProfileClick(
        onSuccessful: () -> Unit,
    ) {
        viewModelScope.launch {
            screenState = screenState.copy(isUpdating = true)
            val updateProfileResult = updateProfileUseCase(screenState.userProfile)

            updateProfileResult.let {
                screenState = screenState.copy(
                    firstNameError = it.firstNameError,
                    lastNameError = it.lastNameError,
                    bioError = it.bioError,
                    profileImageError = it.profileImageError,
                    coverImageError = it.coverImageError
                )
            }

            when (updateProfileResult.result) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = updateProfileResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(R.string.profile_create_successfully)
                        )
                    )
                    onSuccessful()
                }

                null -> {}
            }

            screenState = screenState.copy(isUpdating = false)
        }
    }

    private fun onFirstNameChange(name: String) {
        screenState = screenState.copy(
            userProfile = screenState.userProfile.copy(firstName = name)
        )
    }

    private fun onLastNameChange(name: String) {
        screenState = screenState.copy(
            userProfile = screenState.userProfile.copy(lastName = name)
        )
    }

    private fun onBioChange(bio: String) {
        screenState = screenState.copy(
            userProfile = screenState.userProfile.copy(bio = bio)
        )
    }

    private fun onProfileImageChange(image: Uri?) {
        screenState = screenState.copy(
            userProfile = screenState.userProfile.copy(profileImage = image)
        )
    }

    private fun onCoverImageChange(image: Uri?) {
        screenState = screenState.copy(
            userProfile = screenState.userProfile.copy(coverImage = image)
        )
    }

    fun onEvent(event: UpdateProfileDialogEvents) {
        when (event) {
            is UpdateProfileDialogEvents.OnFirstNameChanged -> {
                onFirstNameChange(event.name)
            }

            is UpdateProfileDialogEvents.OnLastNameChanged -> {
                onLastNameChange(event.name)
            }

            is UpdateProfileDialogEvents.OnBioChanged -> {
                onBioChange(event.name)
            }

            is UpdateProfileDialogEvents.OnProfileUpdateClick -> {
                onUpdateProfileClick(
                    onSuccessful = event.onSuccessful
                )
            }

            is UpdateProfileDialogEvents.OnCoverImageChanged -> {
                onCoverImageChange(event.uri)
            }

            is UpdateProfileDialogEvents.OnProfileImageChanged -> {
                onProfileImageChange(event.uri)
            }
        }
    }
}