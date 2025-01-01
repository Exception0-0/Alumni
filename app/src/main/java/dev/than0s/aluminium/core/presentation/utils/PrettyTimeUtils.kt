package dev.than0s.aluminium.core.presentation.utils

import org.ocpsoft.prettytime.PrettyTime
import java.util.Date

object PrettyTimeUtils {
    private val prettyTime by lazy { PrettyTime() }
    fun getPrettyTime(timestamp: Long): String {
        return prettyTime.format(Date(timestamp))
    }
}
