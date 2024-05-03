package com.kevinputro.core.extension

import java.util.Locale

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
