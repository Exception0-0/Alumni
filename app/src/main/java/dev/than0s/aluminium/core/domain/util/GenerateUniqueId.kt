package dev.than0s.aluminium.core.domain.util

import java.util.UUID

fun generateUniqueId(): String {
    return UUID.randomUUID().toString()
}