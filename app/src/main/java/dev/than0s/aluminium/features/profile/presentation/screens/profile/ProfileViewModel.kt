package dev.than0s.aluminium.features.profile.presentation.screens.profile

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.core.presentation.utils.SnackbarController
import dev.than0s.aluminium.core.presentation.utils.SnackbarEvent
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.core.domain.use_case.GetUserUseCase
import dev.than0s.aluminium.features.profile.domain.use_cases.SetProfileUseCase
import dev.than0s.aluminium.features.profile.presentation.screens.util.ProfileTabScreen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val profileUseCase: GetUserUseCase,
    private val updateProfileUseCase: SetProfileUseCase,
) : ViewModel() {
    val profileScreenArgs = savedStateHandle.toRoute<Screen.ProfileScreen>()
    var screenState by mutableStateOf(ProfileState())

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            when (val result = profileUseCase(profileScreenArgs.userId)) {
                is Resource.Error -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    screenState = screenState.copy(
                        user = result.data!!
                    )
                }
            }
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun onUpdateProfileClick() {
        viewModelScope.launch {
            screenState = screenState.copy(isUpdating = true)
            val updateProfileResult = updateProfileUseCase(screenState.dialogUser)

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
                            message = UiText.StringResource(R.string.successfully_profile_update)
                        )
                    )
                    onUpdateProfileDismissRequest()
                    loadProfile()
                }

                null -> {}
            }

            screenState = screenState.copy(isUpdating = false)
        }
    }

    private fun onFirstNameChange(name: String) {
        screenState = screenState.copy(
            dialogUser = screenState.dialogUser.copy(firstName = name)
        )
    }

    private fun onLastNameChange(name: String) {
        screenState = screenState.copy(
            dialogUser = screenState.dialogUser.copy(lastName = name)
        )
    }

    private fun onBioChange(bio: String) {
        screenState = screenState.copy(
            dialogUser = screenState.dialogUser.copy(bio = bio)
        )
    }

    private fun onProfileImageChange(image: Uri?) {
        screenState = screenState.copy(
            dialogUser = screenState.dialogUser.copy(profileImage = image)
        )
    }

    private fun onCoverImageChange(image: Uri?) {
        screenState = screenState.copy(
            dialogUser = screenState.dialogUser.copy(coverImage = image)
        )
    }

    private fun onUpdateProfileDismissRequest() {
        screenState = screenState.copy(updateProfileDialog = false)
    }

    private fun onEditProfileClick() {
        copyUserToDialog()
        screenState = screenState.copy(updateProfileDialog = true)
    }

    private fun copyUserToDialog() {
        screenState = screenState.copy(
            dialogUser = screenState.user
        )
    }

    private fun onTabChanged(tabIndex: Int) {
        screenState = screenState.copy(tabRowSelectedIndex = tabIndex)
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.OnFirstNameChanged -> {
                onFirstNameChange(event.name)
            }

            is ProfileEvents.OnLastNameChanged -> {
                onLastNameChange(event.name)
            }

            is ProfileEvents.OnBioChanged -> {
                onBioChange(event.name)
            }

            is ProfileEvents.OnProfileUpdateClick -> {
                onUpdateProfileClick()
            }

            is ProfileEvents.OnLoadProfile -> {
                loadProfile()
            }

            is ProfileEvents.OnCoverImageChanged -> {
                onCoverImageChange(event.uri)
            }

            is ProfileEvents.OnEditProfileClick -> {
                onEditProfileClick()
            }

            is ProfileEvents.OnProfileImageChanged -> {
                onProfileImageChange(event.uri)
            }

            is ProfileEvents.OnUpdateDialogDismissRequest -> {
                onUpdateProfileDismissRequest()
            }

            is ProfileEvents.OnTabChanged -> {
                onTabChanged(event.tabIndex)
            }
        }
    }
}