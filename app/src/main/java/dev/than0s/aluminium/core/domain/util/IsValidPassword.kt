package dev.than0s.aluminium.core.domain.util

private val hasUppercase = Regex(".*[A-Z].*")
private val hasLowercase = Regex(".*[a-z].*")
private val hasDigit = Regex(".*[0-9].*")
private val hasSpecialChar = Regex(".*[!@#\$%^&*(),.?\":_{}|<>].*")

fun isValidPassword(password: String): Boolean {
    // Check for minimum length
    if (password.length < TextFieldLimits.MIN_PASSWORD) return false

    // Validate each condition
    return hasUppercase.containsMatchIn(password) &&
            hasLowercase.containsMatchIn(password) &&
            hasDigit.containsMatchIn(password) &&
            hasSpecialChar.containsMatchIn(password)
}