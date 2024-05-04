package com.kevinputro.musicplayer.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinputro.core.base.BaseViewModel
import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import com.kevinputro.core.extension.asLiveData
import com.kevinputro.core.extension.logDebug
import com.kevinputro.core.model.WelcomeCard
import com.kevinputro.core.utils.Event
import com.kevinputro.core.utils.ViewModelUtils
import com.kevinputro.entity.response.SongResponse
import com.kevinputro.musicplayer.model.ContentSong
import com.kevinputro.musicplayer.repository.MusicPlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
  viewModelUtils: ViewModelUtils,
  private val musicPlayerRepository: MusicPlayerRepository,
) : BaseViewModel(viewModelUtils) {

  private val songResponse = ArrayList<SongResponse>()
  private val songList = ArrayList<ContentSong>()
  private var current: ContentSong? = null

  private val _homeContents = MutableLiveData<Event<List<DelegateAdapterItem>>>()
  val homeContents = _homeContents.asLiveData()

  private val _activeSong = MutableLiveData<Event<ContentSong?>>()
  val activeSong = _activeSong.asLiveData()

  private val _trackProgress = MutableLiveData<Event<Int>>()
  val trackProgress = _trackProgress.asLiveData()

  fun onStart() {
    _homeContents.postValue(Event(listOf(WelcomeCard())))
  }

  fun selectedSong(item: ContentSong) {
    current = item
    setPlaying(item)
    _activeSong.postValue(Event(item))
  }

  fun selectPreviousSong() {
    if (current == null) return
    if (songList.isEmpty()) return
    val currentIndex = songList.indexOf(current)
    if (currentIndex == 0) return
    selectedSong(songList[currentIndex - 1])
  }

  fun selectNextSong() {
    if (current == null) return
    if (songList.isEmpty()) return
    val currentIndex = songList.indexOf(current)
    if (currentIndex == songList.lastIndex) return
    selectedSong(songList[currentIndex + 1])
  }

  fun doSearch(keyword: String) {
    viewModelScope.launch(exceptionHandler) {
      musicPlayerRepository.getMusicItems(term = keyword)
        .onStart {
          _loading.postValue(Event(true))
        }
        .collect { list ->
          _loading.postValue(Event(false))
          songList.clear()
          songList.addAll(list.map { ContentSong.parseEntity(it) })
          _homeContents.postValue(Event(songList))

          songResponse.clear()
          songResponse.addAll(list)
        }
    }
  }

  private fun setPlaying(song: ContentSong) {
    if (songResponse.isEmpty()) return
    val newList = ArrayList<ContentSong>()
    newList.addAll(songResponse.map {
      ContentSong.parseEntity(it, it.trackId != null && it.trackId.toString() == song.itemId)
    })
    _homeContents.postValue(Event(newList))
  }
}
