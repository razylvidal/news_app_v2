package com.androidapp.newsclientappcleanarchitecture.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeDifference(format: String?): String {
    val currentTimeInHours = Instant.now().atZone(ZoneId.of("Asia/Manila"))
    val newsTimeInHours = Instant.parse(format).atZone(ZoneId.of("Asia/Manila"))
    val hoursDifference = Duration.between(currentTimeInHours, newsTimeInHours)
    val timeDifference = when(hoursDifference.toHours().toString()){
        "0" -> " " + hoursDifference.toMinutes().toString().substring(1) + " minutes ago"
        "1" -> " " + hoursDifference.toHours().toString().substring(1) + " hour ago"
        else -> " " + hoursDifference.toHours().toString().substring(1) + " hours ago"
    }
    return timeDifference
}

fun getPublishedDate(format: String?): String {
    return " " + format?.substring(0, format.indexOf('T', 0))
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, MMMM d")
    return dateFormat.format(calendar.time).toString()
}