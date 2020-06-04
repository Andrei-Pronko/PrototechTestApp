package com.mentarey.prototech.test.app.utils

import java.text.SimpleDateFormat
import java.util.*

private const val summaryDateFormatString = "dd.MM.yyyy HH:mm:ss"

private val summaryDateFormat: SimpleDateFormat
    get() = SimpleDateFormat(summaryDateFormatString, Locale.US)

val Long.toStringDate: String
    get() = summaryDateFormat.format(this)