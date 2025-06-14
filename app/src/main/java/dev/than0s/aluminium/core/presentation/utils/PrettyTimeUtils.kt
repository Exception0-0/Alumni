package dev.than0s.aluminium.core.presentation.utils

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PrettyTimeUtils {
    private val prettyTime by lazy { PrettyTime() }
    private val dateTimeFormat by lazy {
        SimpleDateFormat("dd.MM.yyyy h:mm a", Locale.getDefault())
    }
    private val timeFormat by lazy {
        SimpleDateFormat("h:mm a", Locale.getDefault())
    }

    fun getPrettyTime(timestamp: Long): String {
        return prettyTime.format(Date(timestamp))
    }

    fun getFormatedDateAndTime(time: Long): String {
        val date = Date(time)
        return dateTimeFormat.format(date)
    }

    fun getFormatedTime(time: Long): String {
        val date = Date(time)
        return timeFormat.format(date)
    }
}
