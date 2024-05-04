package com.kevinputro.data.utils

object ApiConst {
  const val HEADER_ACCEPT = "Accept"
  const val HEADER_AUTHORIZATION = "Authorization"

  const val HEADER_ACCEPT_VALUE = "application/json"

  const val HEADER_NO_AUTH = "No-Auth"

  fun getBearer(token: String) = "Bearer $token"
}
