package com.kevinputro.core.utils

import android.content.Context
import com.kevinputro.blutunes.core.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import javax.inject.Inject

interface ErrorParser {

  /**
   * Common error message to show
   */
  val commonMessage: String

  /**
   * This function will parse network exception or internal android exception that will
   * translating it into human readable message. Recording an exception into crashlytics
   * is highly recommended.
   *
   * Any unknown type of exception will use [commonMessage]
   */
  fun parseError(throwable: Throwable): ErrorModel

}

class ErrorParserImpl @Inject constructor(
  @ApplicationContext context: Context
) : ErrorParser {

  override val commonMessage: String = "An unknown error occurred, please try again later…"

  override fun parseError(throwable: Throwable): ErrorModel = when (throwable) {
    is HttpException -> parseRetrofitHttpException(throwable)
    else -> ErrorModel.parseError(commonMessage)
  }

  private fun parseRetrofitHttpException(httpException: HttpException): ErrorModel {

    val jsonObject: JSONObject? = try {
      httpException.response()?.errorBody()?.bytes()?.let {
        String(it)
      }?.let { json -> JSONObject(json) }
    } catch (e: JSONException) {
      null
    } catch (e: IndexOutOfBoundsException) {
      null
    } catch (e: IOException) {
      null
    }

    val model: ErrorModel = jsonObject?.let {
      return@let try {
        ErrorModel.parseError(
          message = it.getString(KEY_MESSAGE),
          code = httpException.code(),
          cause = httpException
        )
      } catch (e: IOException) {
        null
      } catch (e: NullPointerException) {
        null
      } catch (e: IndexOutOfBoundsException) {
        null
      } catch (e: EOFException) {
        null
      } catch (e: IllegalArgumentException) {
        null
      } catch (e: JSONException) {
        null
      }
    } ?: ErrorModel.parseError(commonMessage)

    return model
  }

  private companion object {
    const val KEY_MESSAGE = "error_message"
  }

}
