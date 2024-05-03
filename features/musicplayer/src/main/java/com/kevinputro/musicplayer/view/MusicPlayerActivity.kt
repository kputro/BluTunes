package com.kevinputro.musicplayer.view

import android.os.Bundle
import com.kevinputro.blutunes.musicplayer.databinding.ActivityMusicPlayerBinding
import com.kevinputro.core.base.BaseActivity
import com.kevinputro.core.delegate.viewBinding
import com.kevinputro.musicplayer.navigation.MusicPlayerNavigationInner
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicPlayerActivity : BaseActivity<ActivityMusicPlayerBinding>() {

  override val binding by viewBinding(ActivityMusicPlayerBinding::inflate)

  @Inject
  internal lateinit var innerNavigation: MusicPlayerNavigationInner

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    innerNavigation.openHomePage(supportFragmentManager)
  }
}
