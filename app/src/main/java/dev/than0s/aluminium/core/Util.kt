package dev.than0s.aluminium.core

import android.net.Uri

var currentUserId: String? = null
    private set
var currentUserRole: String? = null
    private set

fun setCurrentUserId(id: String?) {
    currentUserId = id
}

fun setCurrentUserRole(role: String?) {
    currentUserRole = role
}

fun isLocalUri(file: Uri): Boolean {
    return file.scheme == "file" || file.scheme == "content" || file.scheme == "android.resource"
}