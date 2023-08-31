package com.vti.messageapp.shared.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun Date.getRelativeTimeSpanString(): String {
    return if (DateUtils.isToday(time)) {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        inputFormat.format(this)
    } else {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        inputFormat.format(this)
    }
}