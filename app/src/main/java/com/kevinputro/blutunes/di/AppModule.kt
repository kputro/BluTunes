package com.kevinputro.blutunes.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kevinputro.blutunes.BuildConfig
import com.kevinputro.core.di.annotations.ApplicationCoroutineScope
import com.kevinputro.core.utils.DispatcherProvider
import com.kevinputro.data.di.TagInjection as DataInjection
import com.kevinputro.core.di.TagInjection as CoreInjection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Named(DataInjection.IS_DEBUG_MODE)
  fun provideDebugMode(): Boolean = BuildConfig.DEBUG

  @Singleton
  @Provides
  fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
  }

  @Provides
  fun provideResources(@ApplicationContext context: Context): Resources = context.resources

  @Provides
  fun provideDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
    override val main: CoroutineDispatcher get() = Dispatchers.Main
    override val io: CoroutineDispatcher get() = Dispatchers.IO
  }

  @Provides
  @ApplicationCoroutineScope
  fun provideApplicationCoroutineScope(): CoroutineScope =
    ProcessLifecycleOwner.get().lifecycleScope

  @Provides
  @Named(DataInjection.APPLICATION_CONTEXT)
  fun provideApplicationContext(@ApplicationContext context: Context): Context = context

  @Provides
  @Named(CoreInjection.APP_VERSION_CODE)
  fun provideAppVersionCode(): Int = BuildConfig.VERSION_CODE

  @Provides
  @Named(CoreInjection.APPLICATION_ID)
  fun provideApplicationID(): String = BuildConfig.APPLICATION_ID

  @Provides
  @Named(CoreInjection.APP_VERSION_NAME)
  fun provideAppVersionName(): String = BuildConfig.VERSION_NAME
}
