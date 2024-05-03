package com.kevinputro.core.utils

class ErrorModel @JvmOverloads internal constructor(
  message: String?,
  cause: Throwable? = null,
  val code: Int = UNDEFINED,
) : Error(message, cause) {

  companion object {
    const val UNDEFINED = -1

    fun parseError(message: String) = ErrorModel(message)

    fun parseError(message: String, code: Int, cause: Throwable) = ErrorModel(
      message, cause, code
    )

    fun parseError(throwable: Throwable) = ErrorModel(throwable.message, throwable)
  }

}
