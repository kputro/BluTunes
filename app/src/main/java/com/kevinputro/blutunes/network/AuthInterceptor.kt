package com.kevinputro.blutunes.network

import com.kevinputro.data.utils.ApiConst
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor() : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = chain.request().newBuilder()
    builder.addHeader(ApiConst.HEADER_ACCEPT, ApiConst.HEADER_ACCEPT_VALUE)

    // TODO IF WE NEED TOKEN IN HEADER CREATE HERE

    return chain.proceed(builder.build())
  }

}
