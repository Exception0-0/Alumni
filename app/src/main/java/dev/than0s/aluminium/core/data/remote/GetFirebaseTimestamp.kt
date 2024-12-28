package dev.than0s.aluminium.core.data.remote

import com.google.firebase.Timestamp
import java.util.Date

fun getFirebaseTimestamp(timeMillis: Long): Timestamp {
    return Timestamp(Date(timeMillis))
}