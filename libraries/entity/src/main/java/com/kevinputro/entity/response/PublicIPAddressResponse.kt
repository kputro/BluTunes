package com.kevinputro.entity.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicIPAddressResponse(
  @Json(name = "ip") val ip: String
)
