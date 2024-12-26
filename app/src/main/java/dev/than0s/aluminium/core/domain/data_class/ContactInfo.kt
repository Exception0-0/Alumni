package dev.than0s.aluminium.core.domain.data_class

data class ContactInfo(
    val userId: String = "",
    val email: String? = null,
    val mobile: String? = null,
    val socialHandles: String? = null
)
