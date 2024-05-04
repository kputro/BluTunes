package com.kevinputro.musicplayer.di

import com.kevinputro.musicplayer.repository.MusicPlayerRepository
import com.kevinputro.musicplayer.repository.MusicPlayerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicPlayerModule {

  @Binds
  @Singleton
  internal abstract fun provideMusicPlayerRepository(impl: MusicPlayerRepositoryImpl): MusicPlayerRepository

}
