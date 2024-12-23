package dev.than0s.aluminium.core.domain.util

import android.net.Uri

fun isLocalUri(file: Uri): Boolean {
    return file.scheme == "file" || file.scheme == "content" || file.scheme == "android.resource"
}