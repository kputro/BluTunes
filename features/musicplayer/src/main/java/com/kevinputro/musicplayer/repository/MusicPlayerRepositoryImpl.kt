package com.kevinputro.musicplayer.repository

import com.kevinputro.core.utils.DispatcherProvider
import com.kevinputro.data.service.CoreService
import com.kevinputro.entity.response.SongResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class MusicPlayerRepositoryImpl @Inject constructor(
  private val dispatcher: DispatcherProvider,
  private val coreService: CoreService,
) : MusicPlayerRepository {
  override fun getMusicItems(term: String): Flow<List<SongResponse>> = flow {
    emit(coreService.searchMusic(term.replace(" ", "+")).results.orEmpty())
  }.flowOn(dispatcher.io)
}
