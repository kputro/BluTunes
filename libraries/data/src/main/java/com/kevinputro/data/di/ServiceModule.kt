package com.kevinputro.data.di

import com.kevinputro.data.service.CoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

  @Provides
  fun providesCoreService(retrofit: Retrofit): CoreService =
    retrofit.create(CoreService::class.java)
}
