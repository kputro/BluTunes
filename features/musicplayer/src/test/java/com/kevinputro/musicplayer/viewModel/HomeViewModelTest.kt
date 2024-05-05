package com.kevinputro.musicplayer.viewModel

import com.kevinputro.core.utils.DispatcherProvider
import com.kevinputro.core.utils.TestErrorParserImpl
import com.kevinputro.core.utils.ViewModelUtils
import com.kevinputro.entity.response.SongResponse
import com.kevinputro.musicplayer.dummy.MusicPlayerDummy
import com.kevinputro.musicplayer.repository.MusicPlayerRepository
import com.kevinputro.musicplayer.view.home.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class HomeViewModelTest {

  @Mock
  private lateinit var mockMusicPlayerRepository: MusicPlayerRepository

  private lateinit var homeViewModel: HomeViewModel

  private val dispatcherProvider = object : DispatcherProvider {
    override val main: CoroutineDispatcher
      get() = Dispatchers.Main
    override val io: CoroutineDispatcher
      get() = Dispatchers.IO
  }

  @BeforeEach
  fun setup() {
    MockitoAnnotations.initMocks(this)
    homeViewModel = HomeViewModel(
      ViewModelUtils(dispatcherProvider, TestErrorParserImpl()),
      mockMusicPlayerRepository,
    )
  }

  @Test
  fun searchSongByName_Success() {
    Mockito.`when`(mockMusicPlayerRepository.getMusicItems("coldplay"))
      .thenReturn(flow { emit(MusicPlayerDummy.createDummySongItems()) })

    runBlocking {
      val actual = mutableListOf<SongResponse>()
      mockMusicPlayerRepository.getMusicItems("coldplay")
        .collect {
          actual.addAll(it)
        }
      val expected = MusicPlayerDummy.createDummySongItems()

      Assertions.assertEquals(expected, actual)
    }
  }

  @Test
  fun searchSongByName_Error() {
    Mockito.`when`(mockMusicPlayerRepository.getMusicItems("coldplay"))
      .thenReturn(flow { throw Throwable("Error") })

    runBlocking {
      var actual = ""
      mockMusicPlayerRepository.getMusicItems("coldplay")
        .catch {
          actual = it.message.orEmpty()
        }.collect {}
      val expected = "Error"
      Assertions.assertEquals(expected, actual)
    }
  }
}
