package dev.than0s.aluminium.core

var currentUserId: String? = null
    private set

var currentUserRole: Role = Role.Anonymous
    private set

fun setCurrentUserId(id: String?) {
    currentUserId = id
}

fun setCurrentUserRole(role: Role) {
    currentUserRole = role
}

enum class Role {
    Student,
    Staff,
    Alumni,
    Admin,
    Anonymous,
}

enum class Course {
    MCA,
    MBA
}

const val POST_IMAGES_COUNT = 5