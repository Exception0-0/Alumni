package dev.than0s.aluminium.features.profile.domain.data_class

import dev.than0s.aluminium.core.Course
import dev.than0s.aluminium.core.Role

data class AboutInfo(
    val role: Role = Role.Student,
    val course: Course? = null,
    val batchFrom: String? = null,
    val batchTo: String? = null,
)
