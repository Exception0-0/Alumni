package dev.than0s.aluminium.core.data_class

import com.google.firebase.firestore.DocumentId

data class RegistrationForm(
    @DocumentId val id: String = "",
    val category: String = "",
    val rollNo: String = "",
    val email: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batch: String = "",
    val status: RegistrationFromStatus = RegistrationFromStatus()
)

data class RegistrationFromStatus(
    val approvalStatus: Boolean? = null,
    val accountGeneratedStatus: Boolean? = null,
    val emailSendStatus: Boolean = false
)