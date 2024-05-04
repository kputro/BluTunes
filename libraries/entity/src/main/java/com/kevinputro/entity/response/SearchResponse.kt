package com.kevinputro.entity.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse<T>(
  @Json(name = "resultCount") val resultCount: Int?,
  @Json(name = "results") val results: List<T>?,
)
