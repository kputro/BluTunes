package com.kevinputro.musicplayer.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

internal interface MusicPlayerNavigationInner {

  val containerId: Int @IdRes get

  fun openHomePage(fm: FragmentManager)
}
