package com.example.driver.common

import com.example.driver.common.Constants.SERVER_TIMEZONE
import java.text.SimpleDateFormat
import java.util.*

class Extensions {

    companion object {
        val dateTimeApiFormat = SimpleDateFormat(Constants.DATETIME_API_FORMAT, Locale.ROOT).apply {
            timeZone = TimeZone.getTimeZone(SERVER_TIMEZONE)
        }
        val dateTimeFormat = SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.ROOT).apply {
            timeZone = TimeZone.getDefault()
        }
    }
}

fun Date.formatToApiDateTimeString(): String {
    return Extensions.dateTimeApiFormat.format(this)
}

fun String.parseToApiDateTime(): Date? =
    try {
        Extensions.dateTimeApiFormat.parse(this)
    } catch(e: Exception) {
        null
    }

fun Date.formatToDateTimeString(): String {
    return Extensions.dateTimeFormat.format(this)
}

fun String.parseToDateTime(): Date? =
    try {
        Extensions.dateTimeFormat.parse(this)
    } catch(e: Exception) {
        null
    }