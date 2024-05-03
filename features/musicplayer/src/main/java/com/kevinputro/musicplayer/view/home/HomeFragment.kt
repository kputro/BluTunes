package com.kevinputro.musicplayer.view.home

import androidx.fragment.app.viewModels
import com.kevinputro.blutunes.musicplayer.BuildConfig
import com.kevinputro.blutunes.musicplayer.databinding.FragmentHomeBinding
import com.kevinputro.core.base.BaseFragment
import com.kevinputro.core.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  override val binding by viewBinding(FragmentHomeBinding::inflate)

  private val viewModel by viewModels<HomeViewModel>()

  companion object {
    val TAG get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${HomeFragment::class.java.simpleName}"

    fun newInstance() = HomeFragment()
  }
}
