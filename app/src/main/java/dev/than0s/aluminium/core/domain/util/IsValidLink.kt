package dev.than0s.aluminium.core.domain.util

private val urlRegex = Regex("^(https)://[a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=%]+\$")

fun isValidLink(link: String): Boolean {
    return link.matches(urlRegex)
}