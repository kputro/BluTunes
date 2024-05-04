package com.kevinputro.core.extension

import com.kevinputro.core.utils.Constant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.abs

fun Date.toStringFormat(targetFormat: String = Constant.FORMAT_YEAR_ONLY): String {
  val simpleDateFormat = SimpleDateFormat(targetFormat, Constant.defaultLocale)
  return simpleDateFormat.format(this)
}

fun getFirstDateOfTheMonth(): Date {
  val calendar = Calendar.getInstance()
  calendar.set(Calendar.DAY_OF_MONTH, 1)
  return calendar.time
}

/**
 * Will convert [Date] to Elapsed time
 * return List of [Long]
 * list[0] days, list[1] hours, list[2] minutes, list[3] seconds
 */
fun Date.getElapsedTime(from: Date = Date()): List<Long> {
  var deltaMillis = from.time - this.time
  val secondsInMillis = 1000
  val minutesInMillis = secondsInMillis * 60
  val hoursInMillis = minutesInMillis * 60
  val daysInMillis = hoursInMillis * 24

  val elapsedDays = deltaMillis / daysInMillis
  deltaMillis %= daysInMillis

  val elapsedHours = deltaMillis / hoursInMillis
  deltaMillis %= hoursInMillis

  val elapsedMinutes = deltaMillis / minutesInMillis
  deltaMillis %= minutesInMillis

  val elapsedSeconds = deltaMillis / secondsInMillis

  logDebug(
    "ElapsedTime[days=$elapsedDays, hours=$elapsedHours, minutes=$elapsedMinutes, seconds=$elapsedSeconds]",
    "TIME"
  )
  return listOf(elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
}
