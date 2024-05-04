package com.kevinputro.blutunes.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kevinputro.blutunes.BuildConfig
import com.kevinputro.blutunes.network.AuthInterceptor
import com.squareup.moshi.Moshi
import com.kevinputro.data.di.TagInjection as DataInjection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
  @Provides
  @Named(DataInjection.BASE_URL)
  fun provideBaseUrl(): String = BuildConfig.BASE_URL

  @Provides
  @Named(DataInjection.AUTH_INTERCEPTOR)
  fun provideAuthInterceptor(impl: AuthInterceptor): Interceptor = impl

  @Provides
  @Named(DataInjection.LOGGING_INTERCEPTOR)
  fun provideHttpLoggingInterceptor(
    @Named(DataInjection.IS_DEBUG_MODE) debugMode: Boolean,
  ): Interceptor = if (debugMode) HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  } else Interceptor { chain -> chain.proceed(chain.request()) }

  @Provides
  @Named(DataInjection.CHUCKER_INTERCEPTOR)
  fun provideChuckerInterceptor(
    @Named(DataInjection.IS_DEBUG_MODE) debugMode: Boolean,
    @ApplicationContext context: Context,
  ): Interceptor = if (debugMode) ChuckerInterceptor.Builder(context).build()
  else Interceptor { chain -> chain.proceed(chain.request()) }

  @Provides
  fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory {
    return MoshiConverterFactory.create(moshi)
  }
}
