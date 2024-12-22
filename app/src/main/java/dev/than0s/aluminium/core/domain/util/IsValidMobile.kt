package dev.than0s.aluminium.core.domain.util

private val phoneRegex = "^\\+?[0-9]{10,15}$".toRegex()

fun isValidPhoneNumber(phone: String): Boolean {
    return phone.matches(phoneRegex)
}