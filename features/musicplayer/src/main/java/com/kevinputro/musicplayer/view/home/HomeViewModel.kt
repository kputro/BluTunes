package com.kevinputro.musicplayer.view.home

import com.kevinputro.core.base.BaseViewModel
import com.kevinputro.core.utils.ViewModelUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
  viewModelUtils: ViewModelUtils,
) : BaseViewModel(viewModelUtils) {

  fun onStart() {

  }
}
