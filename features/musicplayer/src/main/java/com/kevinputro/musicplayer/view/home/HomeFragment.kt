package com.kevinputro.musicplayer.view.home

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinputro.blutunes.musicplayer.BuildConfig
import com.kevinputro.blutunes.musicplayer.databinding.FragmentHomeBinding
import com.kevinputro.core.adapter.WelcomeCardAdapter
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
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import com.kevinputro.blutunes.core.R as CR


@AndroidEntryPoint
internal class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  override val binding by viewBinding(FragmentHomeBinding::inflate)

  private val viewModel by viewModels<HomeViewModel>()

  private var mediaPlayer: MediaPlayer? = null

  private val trackHandler: Handler = Handler(Looper.getMainLooper())

  private val contentAdapter by lazy {
    CompositeAdapter.Builder()
      .add(ContentSongAdapter {
        viewModel.selectedSong(it)
      })
      .add(WelcomeCardAdapter())
      .build()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    bindViewModels()
    bindViewEvents()

    trackIndicatorPosition()
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
    observe(viewModel.homeContents, EventObserver {
      contentAdapter.submitList(it)
    })
    observe(viewModel.activeSong, EventObserver {
      setupControlDeckSong(it)
      playSong(it)
    })
    observe(viewModel.trackProgress, EventObserver {
      binding.controlDeck.seekBar.progress =
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
        viewModel.selectPreviousSong()
      }
      btnPlay.setOnClickListener {
        playOrPauseSong()
      }
      btnNext.setOnClickListener {
        viewModel.selectNextSong()
      }
      seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
          if (mediaPlayer != null && fromUser) {
            mediaPlayer!!.seekTo(progress * 200)
          }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
          // Not used at the moment..
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
          // Not used at the moment..
        }
      })
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

  private fun playSong(cs: ContentSong?) {
    val song = cs ?: return
    MainScope().launch {
      delay(100)
      startPlay(song.streamUrl)
    }
  }

  private fun playOrPauseSong() {
    val mp = mediaPlayer ?: return
    try {
      if (!mp.isPlaying) {
        mp.start()
        binding.controlDeck.btnPlay.setImageResource(CR.drawable.ic_control_pause)
        return
      }
      mp.pause()
      binding.controlDeck.btnPlay.setImageResource(CR.drawable.ic_control_play)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun startPlay(streamUrl: String) {
    if (mediaPlayer?.isPlaying == true) {
      mediaPlayer?.apply {
        stop()
        reset()
        release()
      }
    }

    resetMediaPlayer()

    try {
      mediaPlayer?.let {
        it.setDataSource(streamUrl)

        it.prepare()
        it.setOnCompletionListener {
          binding.controlDeck.seekBar.progress = 0
          binding.controlDeck.btnPlay.setImageResource(CR.drawable.ic_control_play)
        }

        binding.controlDeck.seekBar.max = it.duration / 200

        it.start()

        binding.controlDeck.seekBar.progress = 0
        binding.controlDeck.btnPlay.setImageResource(CR.drawable.ic_control_pause)
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun resetMediaPlayer() {
    // Setup MediaPlayer
    mediaPlayer = MediaPlayer()
    mediaPlayer?.setAudioAttributes(
      AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()
    )
  }

  private fun trackIndicatorPosition() {
    requireActivity().runOnUiThread(object : Runnable {
      override fun run() {
        if (mediaPlayer != null) {
          if (mediaPlayer!!.isPlaying) {
            val currentPosition: Int = mediaPlayer!!.currentPosition / 200
            binding.controlDeck.seekBar.progress = currentPosition
          }
        }
        trackHandler.postDelayed(this, 200)
      }
    })
  }

  companion object {
    val TAG get() = "TAG.${BuildConfig.LIBRARY_PACKAGE_NAME}.${HomeFragment::class.java.simpleName}"

    fun newInstance() = HomeFragment()
  }
}
