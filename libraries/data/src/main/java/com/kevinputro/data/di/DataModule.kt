package com.kevinputro.data.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {

  /**
   * Provide our moshi instance in case we need to use custom adapter for later use
   */
  @Provides
  fun provideMoshi(): Moshi = Moshi.Builder().build()

  @Provides
  fun provideOkhttpClientBuilder(
    @Named(TagInjection.IS_DEBUG_MODE) debugMode: Boolean,
    @Named(TagInjection.AUTH_INTERCEPTOR) authInterceptor: Interceptor,
    @Named(TagInjection.LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor,
    @Named(TagInjection.CHUCKER_INTERCEPTOR) chuckerInterceptor: Interceptor
  ): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.MINUTES)
      .writeTimeout(1, TimeUnit.MINUTES)
      .readTimeout(1, TimeUnit.MINUTES)

    // interceptor is ordered, so the order is important
    if (debugMode) {
      builder.addInterceptor(loggingInterceptor)
      builder.addInterceptor(chuckerInterceptor)
    }

    builder.addInterceptor(authInterceptor)

    return builder
  }

  @Singleton
  @Provides
  fun provideRetrofit(
    moshiConverterFactory: Converter.Factory,
    clientBuilder: OkHttpClient.Builder,
    @Named(TagInjection.BASE_URL) baseUrl: String,
  ): Retrofit {
    return Retrofit.Builder()
      .client(clientBuilder.build())
      .baseUrl(baseUrl)
      .addConverterFactory(moshiConverterFactory)
      .build()
  }

}
