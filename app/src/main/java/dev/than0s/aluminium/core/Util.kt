package dev.than0s.aluminium.core

import android.net.Uri

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

fun isLocalUri(file: Uri): Boolean {
    return file.scheme == "file" || file.scheme == "content" || file.scheme == "android.resource"
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