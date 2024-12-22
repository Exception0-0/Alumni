package dev.than0s.aluminium.features.profile.presentation.screens.create_profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SnackbarController
import dev.than0s.aluminium.core.SnackbarEvent
import dev.than0s.aluminium.core.UiText
import dev.than0s.aluminium.features.profile.domain.use_cases.SetProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileScreenViewModel @Inject constructor(
    private val updateProfileUseCase: SetProfileUseCase,
) : ViewModel() {
    var screenState by mutableStateOf(CreateProfileScreenState())

    private fun onSaveProfileClick(
        restartApp: () -> Unit,
    ) {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
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
                    restartApp()
                }

                null -> {}
            }

            screenState = screenState.copy(isLoading = false)
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

    fun onEvent(event: CreateProfileEvents) {
        when (event) {
            is CreateProfileEvents.OnFirstNameChanged -> {
                onFirstNameChange(event.name)
            }

            is CreateProfileEvents.OnLastNameChanged -> {
                onLastNameChange(event.name)
            }

            is CreateProfileEvents.OnBioChanged -> {
                onBioChange(event.name)
            }

            is CreateProfileEvents.OnProfileSaveClick -> {
                onSaveProfileClick(event.restartApp)
            }

            is CreateProfileEvents.OnCoverImageChanged -> {
                onCoverImageChange(event.uri)
            }

            is CreateProfileEvents.OnProfileImageChanged -> {
                onProfileImageChange(event.uri)
            }
        }
    }
}