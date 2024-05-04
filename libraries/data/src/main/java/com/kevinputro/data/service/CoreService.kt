package com.kevinputro.data.service

import com.kevinputro.data.utils.Endpoint
import com.kevinputro.entity.response.PublicIPAddressResponse
import com.kevinputro.entity.response.SearchResponse
import com.kevinputro.entity.response.SongResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CoreService {

  @GET
  suspend fun getPublicIPAddress(
    @Url url: String = "https://api64.ipify.org",
    @Query("format") format: String = "json"
  ): PublicIPAddressResponse

  @GET(Endpoint.SEARCH)
  suspend fun searchMusic(
    @Query("term") term: String = "",
    @Query("entity") entity: String = "song",
  ): SearchResponse<SongResponse>
}
