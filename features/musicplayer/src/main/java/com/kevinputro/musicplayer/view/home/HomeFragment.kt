package com.kevinputro.musicplayer.view.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinputro.blutunes.core.R as CR
import com.kevinputro.blutunes.musicplayer.BuildConfig
import com.kevinputro.blutunes.musicplayer.databinding.FragmentHomeBinding
import com.kevinputro.core.base.BaseFragment
import com.kevinputro.core.delegate.adapter.CompositeAdapter
import com.kevinputro.core.delegate.viewBinding
import com.kevinputro.core.extension.hideKeyboard
import com.kevinputro.core.extension.ifNotEmpty
import com.kevinputro.core.extension.observe
import com.kevinputro.core.utils.EventObserver
import com.kevinputro.musicplayer.adapter.ContentSongAdapter
import com.kevinputro.musicplayer.model.ContentSong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  override val binding by viewBinding(FragmentHomeBinding::inflate)

  private val viewModel by viewModels<HomeViewModel>()

  private val contentAdapter by lazy {
    CompositeAdapter.Builder()
      .add(ContentSongAdapter {
        viewModel.selectedSong(it)
      })
      .build()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    bindViewModels()
    bindViewEvents()
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()
  }

  private fun initView() {
    /// Setup Content Recycler View
    with(binding) {
      val contentLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      rvContent.apply {
        layoutManager = contentLayoutManager
        adapter = contentAdapter
      }
    }
  }

  private fun bindViewModels() {
    observe(viewModel.loading, EventObserver {
      showLoading(it)
    })
    observe(viewModel.errorLiveData, EventObserver {
      it.message?.let(::showToast)
    })
    observe(viewModel.homeContents) {
      contentAdapter.submitList(it)
    }
    observe(viewModel.activeSong, EventObserver {
      setupControlDeckSong(it)
    })
    observe(viewModel.trackProgress, EventObserver {
      binding.controlDeck.trackIndicator.progress =
        if (it < 0) {
          0
        } else if (it > 100) {
          100
        } else it
    })
  }

  private fun bindViewEvents() {
    with(binding.controlDeck) {
      btnPrevious.setOnClickListener {

      }
      btnPlay.setOnClickListener {

      }
      btnNext.setOnClickListener {

      }
    }
    with(binding.searchHeader.layoutSearch) {
      ivSearchClear.setOnClickListener {
        etSearch.setText("")
      }
      etSearch.addTextChangedListener(onTextChanged = { charSeq, _, _, _ ->
        ivSearchClear.isVisible = charSeq.toString().isNotEmpty()
      })
      etSearch.setOnEditorActionListener { v, actionId, _ ->
        when (actionId) {
          EditorInfo.IME_ACTION_SEARCH -> {
            v.text.toString().ifNotEmpty {
              viewModel.doSearch(it)
            }
            v.hideKeyboard()
            true
          }
          else -> false
        }
      }
    }
  }

  private fun setupControlDeckSong(song: ContentSong?) {
    binding.homeControlDeck.isVisible = song != null
    if (song == null) return
    with(binding.controlDeck) {
      ivAlbum.loadImage(
        imgUrl = song.albumImageUrl,
        placeholderImg = CR.drawable.bg_album_placeholder
      )
      tvSongInformation.text =
        song.songArtist
          .plus(" • ")
          .plus(song.songAlbum)
          .plus(" • ")
          .plus(song.songYear)
      tvSongTitle.text = song.songTitle
    }
  }

  companion object {
    val TAG get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${HomeFragment::class.java.simpleName}"

    fun newInstance() = HomeFragment()
  }
}
