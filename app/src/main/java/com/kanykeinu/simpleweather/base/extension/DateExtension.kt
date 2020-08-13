package com.kanykeinu.simpleweather.base.extension

import com.kanykeinu.simpleweather.base.extension.DateFormats.DAY_MONTH_TIME
import java.text.SimpleDateFormat
import java.util.*

object DateFormats {
    const val DAY_MONTH_TIME = "dd MMMM HH:mm"
}

fun Long.asString(format: String = DAY_MONTH_TIME, timezone: String = ""): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    dateFormatter.timeZone = TimeZone.getTimeZone(timezone)
    val date = Date(this*1000)
    return dateFormatter.format(date)
}


