package dev.than0s.aluminium.features.registration.domain.data_class

data class RegistrationRequestStatus(
    val approvalStatus: Boolean? = null,
    val accountGeneratedStatus: Boolean? = null,
    val emailSendStatus: Boolean = false
)