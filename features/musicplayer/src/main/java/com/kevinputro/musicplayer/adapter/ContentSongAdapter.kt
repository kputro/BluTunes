package com.kevinputro.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kevinputro.blutunes.core.R as CR
import com.kevinputro.blutunes.musicplayer.databinding.LayoutItemSongBinding
import com.kevinputro.core.base.DelegateBaseViewHolder
import com.kevinputro.core.delegate.adapter.DelegateAdapter
import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import com.kevinputro.musicplayer.model.ContentSong

internal class ContentSongAdapter(
  private val onClick: ((ContentSong) -> Unit)? = null
) :
  DelegateAdapter<ContentSong, ContentSongAdapter.ContentSongViewHolder>(ContentSong::class.java) {

  override fun createViewHolder(parent: ViewGroup) = ContentSongViewHolder(
    LayoutItemSongBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun bindViewHolder(
    model: ContentSong,
    viewHolder: ContentSongViewHolder,
    payloads: List<DelegateAdapterItem.Payloadable>
  ) {
    viewHolder.bind(model)
  }

  inner class ContentSongViewHolder(
    private val binding: LayoutItemSongBinding
  ) : DelegateBaseViewHolder<ContentSong>(binding.root) {
    override fun bind(data: ContentSong) {
      with(binding) {
        ivAlbum.loadImage(
          imgUrl = data.albumImageUrl,
          placeholderImg = CR.drawable.bg_album_placeholder
        )
        tvSongTitle.text = data.songTitle
        tvSongArtist.text = data.songArtist
        tvSongAlbum.text = data.songAlbum.plus(" • ").plus(data.songYear)
        ivSongControl.setImageResource(
          when (data.isPlaying) {
            true -> CR.drawable.ic_control_pause
            else -> CR.drawable.ic_control_play
          }
        )
        buttonAction.setOnClickListener {
          onClick?.invoke(data)
        }
      }
    }
  }
}
