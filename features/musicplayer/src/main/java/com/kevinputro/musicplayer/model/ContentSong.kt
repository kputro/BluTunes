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
  val itemId: String = UUID.randomUUID().toString(),
  val isPlaying: Boolean = false,
  val source: SongResponse? = null,
) : DelegateAdapterItem {

  override fun id() = itemId

  override fun toString() =
    "ContentSong[title=$songTitle, artist=$songArtist, album=$songAlbum, year=$songYear, albumImageUrl=$albumImageUrl, itemId=$itemId, isPlaying=$isPlaying]"

  override fun content() = toString()

  override fun equals(other: DelegateAdapterItem) = other is ContentSong &&
      other.content() == content()

  companion object {
    fun parseEntity(response: SongResponse): ContentSong = ContentSong(
      albumImageUrl = response.artworkUrl30.orEmpty()
        .ifEmpty { response.artworkUrl60.orEmpty().ifEmpty { response.artworkUrl100.orEmpty() } },
      songTitle = response.trackName.orEmpty(),
      songArtist = response.artistName.orEmpty(),
      songAlbum = response.collectionName.orEmpty(),
      songYear = response.releaseDate.orEmpty()
        .reformatStringDate(targetFormat = Constant.FORMAT_YEAR_ONLY),
      itemId = response.trackId?.toString() ?: UUID.randomUUID().toString(),
      source = response,
    )
  }
}
