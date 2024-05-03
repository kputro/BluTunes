package com.kevinputro.musicplayer.navigation

import androidx.fragment.app.FragmentManager
import com.kevinputro.blutunes.musicplayer.R
import com.kevinputro.core.utils.AppNavigation
import com.kevinputro.musicplayer.view.home.HomeFragment
import javax.inject.Inject

internal class MusicPlayerNavigationInnerImpl @Inject constructor(
  private val appNavigation: AppNavigation
) : MusicPlayerNavigationInner {

  override val containerId: Int
    get() = R.id.container

  override fun openHomePage(fm: FragmentManager) {
    appNavigation.pushFragmentClearBackStack(
      fragmentManager = fm,
      containerId = containerId,
      fragment = HomeFragment.newInstance(),
      fragmentTag = HomeFragment.TAG
    )
  }
}
