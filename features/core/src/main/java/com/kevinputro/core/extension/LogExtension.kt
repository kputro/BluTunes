package com.kevinputro.core.extension

import android.util.Log

inline fun <reified T> T.logDebug(messages: String, tag: String? = null) {
  Log.d(tag ?: T::class.java.simpleName, messages)
}

inline fun <reified T> T.logError(messages: String, tag: String? = null) {
  Log.e(tag ?: T::class.java.simpleName, messages)
}

inline fun <reified T> T.logWarning(messages: String, tag: String? = null) {
  Log.w(tag ?: T::class.java.simpleName, messages)
}
