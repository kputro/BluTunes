package com.kevinputro.musicplayer.model

import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import java.util.UUID

internal data class ContentSong(
  val albumImageUrl: String,
  val songTitle: String,
  val songArtist: String,
  val songAlbum: String,
  val songYear: String,
  val itemId: String = UUID.randomUUID().toString(),
  val isPlaying: Boolean = false,
) : DelegateAdapterItem {

  override fun id() = itemId

  override fun toString() =
    "ContentSong[title=$songTitle, artist=$songArtist, album=$songAlbum, year=$songYear, albumImageUrl=$albumImageUrl, itemId=$itemId, isPlaying=$isPlaying]"

  override fun content() = toString()

  override fun equals(other: DelegateAdapterItem) = other is ContentSong &&
      other.content() == content()
}
