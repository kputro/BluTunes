package com.kevinputro.data.service

import com.kevinputro.entity.response.PublicIPAddressResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CoreService {

  @GET
  suspend fun getPublicIPAddress(
    @Url url: String = "https://api64.ipify.org",
    @Query("format") format: String = "json"
  ): PublicIPAddressResponse
}
