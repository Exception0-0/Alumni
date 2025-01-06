package dev.than0s.aluminium.features.registration.domain.data_class

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role

data class RegistrationForm(
    @DocumentId val id: String = "",
    val role: Role = Role.Student,
    val collegeId: String = "",
    val course: Course? = null,
    val email: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batchFrom: String? = null,
    val batchTo: String? = null,
    val idCardImage: Uri? = null,
    val status: RegistrationRequestStatus = RegistrationRequestStatus()
)