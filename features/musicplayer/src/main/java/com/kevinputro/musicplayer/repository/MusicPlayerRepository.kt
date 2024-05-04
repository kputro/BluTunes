package com.kevinputro.musicplayer.repository

import com.kevinputro.entity.response.SongResponse
import kotlinx.coroutines.flow.Flow

internal interface MusicPlayerRepository {

  fun getMusicItems(term: String): Flow<List<SongResponse>>
}
