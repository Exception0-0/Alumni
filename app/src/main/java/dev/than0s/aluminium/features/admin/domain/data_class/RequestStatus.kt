package dev.than0s.aluminium.features.admin.domain.data_class

data class RequestStatus(
    val approvalStatus: Boolean? = null,
    val accountGeneratedStatus: Boolean? = null,
    val emailSendStatus: Boolean = false
)