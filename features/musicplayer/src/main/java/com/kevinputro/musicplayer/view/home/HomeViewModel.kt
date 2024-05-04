package com.kevinputro.musicplayer.view.home

import androidx.lifecycle.MutableLiveData
import com.kevinputro.core.base.BaseViewModel
import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import com.kevinputro.core.extension.asLiveData
import com.kevinputro.core.utils.Event
import com.kevinputro.core.utils.ViewModelUtils
import com.kevinputro.musicplayer.model.ContentSong
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
  viewModelUtils: ViewModelUtils,
) : BaseViewModel(viewModelUtils) {

  private val _homeContents = MutableLiveData<List<DelegateAdapterItem>>()
  val homeContents = _homeContents.asLiveData()

  private val _activeSong = MutableLiveData<Event<ContentSong?>>()
  val activeSong = _activeSong.asLiveData()

  private val _trackProgress = MutableLiveData<Event<Int>>()
  val trackProgress = _trackProgress.asLiveData()

  fun onStart() {
    // Create song list
    val items = ArrayList<ContentSong>()
    for (i in 1..20) {
      items.add(ContentSong(
        albumImageUrl = "https://lh3.googleusercontent.com/FoVQFdW6zBi3sNA_yZJSV3VTWmi0belhhFzleuEbn27utkirstj1woXHfWmWqkNyHla37ZFbk_F6jvVV=w544-h544-l90-rj",
        songTitle = "Don't Look Back in Anger",
        songAlbum = "(What's The Story) Morning Glory? (Remastered)",
        songArtist = "Oasis",
        songYear = "1995",
      ))
    }
    _homeContents.postValue(items)
  }

  fun selectedSong(item: ContentSong) {
    _activeSong.postValue(Event(item))
  }
}
