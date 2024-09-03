package dev.than0s.aluminium.core.data_class

data class RegistrationForm(
    private val approvalStatus: Boolean? = null,
    val category: String = "",
    val id: String = "",
    val email: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batch: String = "",
)