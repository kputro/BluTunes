package com.kevinputro.musicplayer.model

import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import com.kevinputro.core.extension.ifNotEmpty
import com.kevinputro.core.extension.reformatStringDate
import com.kevinputro.core.utils.Constant
import com.kevinputro.entity.response.SongResponse
import java.util.UUID

internal data class ContentSong(
  val albumImageUrl: String,
  val songTitle: String,
  val songArtist: String,
  val songAlbum: String,
  val songYear: String,
  val streamUrl: String,
  val itemId: String,
  var isPlaying: Boolean = false,
  val source: SongResponse? = null,
) : DelegateAdapterItem {

  override fun id() = itemId

  override fun toString() =
    "ContentSong[title=$songTitle, artist=$songArtist, album=$songAlbum, year=$songYear, streamUrl=$streamUrl, albumImageUrl=$albumImageUrl, itemId=$itemId, isPlaying=$isPlaying]"

  override fun content() = toString()

  override fun equals(other: DelegateAdapterItem) = other is ContentSong &&
      other.content() == content() && other.isPlaying == isPlaying

  companion object {
    fun parseEntity(response: SongResponse, active: Boolean = false): ContentSong = ContentSong(
      albumImageUrl = response.artworkUrl100.orEmpty(),
      songTitle = response.trackName.orEmpty(),
      songArtist = response.artistName.orEmpty(),
      songAlbum = response.collectionName.orEmpty(),
      streamUrl = response.previewUrl.orEmpty(),
      songYear = response.releaseDate.orEmpty()
        .reformatStringDate(targetFormat = Constant.FORMAT_YEAR_ONLY),
      itemId = response.trackId.toString(),
      isPlaying = active,
      source = response,
    )
  }
}
