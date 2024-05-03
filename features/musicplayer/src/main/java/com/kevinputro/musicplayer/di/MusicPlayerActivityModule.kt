package com.kevinputro.musicplayer.di

import com.kevinputro.musicplayer.navigation.MusicPlayerNavigationInnerImpl
import com.kevinputro.musicplayer.navigation.MusicPlayerNavigationInner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class MusicPlayerActivityModule {

  @Binds
  @ActivityScoped
  abstract fun provideInnerNavigation(impl: MusicPlayerNavigationInnerImpl): MusicPlayerNavigationInner
}
