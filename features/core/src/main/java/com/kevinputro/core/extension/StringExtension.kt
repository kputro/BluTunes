package com.kevinputro.core.extension

import com.kevinputro.core.utils.Constant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String?.isNotEmptyOrBlank(): Boolean = if (this == null) false else !(isEmpty() || isBlank())

fun String?.isEmptyOrBlank(): Boolean = if (this == null) true else (isEmpty() || isBlank())

fun String.ifNotEmpty(callback: (String) -> Unit) {
  if (this.isNotEmpty()) {
    callback.invoke(this)
  }
}

fun String?.isJsonString() =
  if (this.isNullOrBlank()) false else startsWith("{") && endsWith("}")

fun String?.toSafeInt(replacement: Int = 0): Int = try {
  this?.toIntOrNull() ?: replacement
} catch (e: Exception) {
  replacement
}

fun String?.capitalized() = this.orEmpty()
  .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun String.toDate(sourceFormat: String = Constant.FORMAT_UTC): Date? {
  return try {
    val simpleDateFormat = SimpleDateFormat(sourceFormat, Constant.defaultLocale)
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    simpleDateFormat.parse(this)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

fun String.reformatStringDate(
  sourceFormat: String = Constant.FORMAT_UTC,
  targetFormat: String = Constant.FORMAT_YEAR_ONLY
): String = this.toDate(sourceFormat)?.toStringFormat(targetFormat) ?: ""
