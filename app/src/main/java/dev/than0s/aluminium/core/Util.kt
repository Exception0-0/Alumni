package dev.than0s.aluminium.core

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