package com.kevinputro.entity.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongResponse(
  @Json(name = "trackId") val trackId: Long?,
  @Json(name = "collectionId") val collectionId: Long?,
  @Json(name = "artistId") val artistId: Long?,
  @Json(name = "artistName") val artistName: String?,
  @Json(name = "collectionName") val collectionName: String?,
  @Json(name = "trackName") val trackName: String?,
  @Json(name = "previewUrl") val previewUrl: String?,
  @Json(name = "artworkUrl100") val artworkUrl100: String?,
  @Json(name = "releaseDate") val releaseDate: String?,
  @Json(name = "primaryGenreName") val primaryGenreName: String?,
)

