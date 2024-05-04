package com.kevinputro.core.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.gone() {
  this?.visibility = View.GONE
}

fun View?.visible() {
  this?.visibility = View.VISIBLE
}

fun View?.invisible() {
  this?.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
  try {
    (context.getSystemService(
      Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
  } catch (e: ClassCastException) {
    e.printStackTrace()
  }
}
