package com.kevinputro.musicplayer.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinputro.core.base.BaseViewModel
import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import com.kevinputro.core.extension.asLiveData
import com.kevinputro.core.utils.Event
import com.kevinputro.core.utils.ViewModelUtils
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

  private val _homeContents = MutableLiveData<List<DelegateAdapterItem>>()
  val homeContents = _homeContents.asLiveData()

  private val _activeSong = MutableLiveData<Event<ContentSong?>>()
  val activeSong = _activeSong.asLiveData()

  private val _trackProgress = MutableLiveData<Event<Int>>()
  val trackProgress = _trackProgress.asLiveData()

  fun onStart() {
    // TODO Create Hint View
  }

  fun selectedSong(item: ContentSong) {
    _activeSong.postValue(Event(item))
  }

  fun doSearch(keyword: String) {
    viewModelScope.launch(exceptionHandler) {
      musicPlayerRepository.getMusicItems(term = keyword)
        .onStart {
          _loading.postValue(Event(true))
        }
        .collect { list ->
          _loading.postValue(Event(false))
          _homeContents.postValue(list.map { ContentSong.parseEntity(it) })
        }
    }
  }
}
