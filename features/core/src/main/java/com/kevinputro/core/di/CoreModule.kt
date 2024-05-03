package com.kevinputro.core.di

import com.kevinputro.core.utils.AppNavigation
import com.kevinputro.core.utils.AppNavigationImpl
import com.kevinputro.core.utils.ErrorParser
import com.kevinputro.core.utils.ErrorParserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

  @Binds
  @Singleton
  internal abstract fun provideAppNavigation(impl: AppNavigationImpl): AppNavigation

  @Binds
  @Singleton
  internal abstract fun provideErrorParser(impl: ErrorParserImpl): ErrorParser
}
