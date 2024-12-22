package dev.than0s.aluminium.core.domain.util

private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

fun isValidEmail(email: String): Boolean {
    return email.matches(emailRegex)
}