package dev.than0s.aluminium.features.registration.domain.data_class

import android.net.Uri
import com.google.firebase.firestore.DocumentId

data class RegistrationForm(
    @DocumentId val id: String = "",
    val category: String = "",
    val rollNo: String = "",
    val email: String = "",
    val mobile: String? = null,
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batchFrom: String = "",
    val batchTo: String = "",
    val idCardImage: Uri? = null,
    val status: RegistrationStatus = RegistrationStatus()
)