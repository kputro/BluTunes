package com.kevinputro.musicplayer.repository

import com.kevinputro.entity.response.SongResponse
import kotlinx.coroutines.flow.Flow

interface MusicPlayerRepository {

  fun getMusicItems(term: String): Flow<List<SongResponse>>
}
