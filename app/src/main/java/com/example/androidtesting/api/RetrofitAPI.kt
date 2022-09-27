package com.example.androidtesting.api

import com.example.androidtesting.model.ImageResponse
import com.example.androidtesting.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apikey: String = Util.API_KEY
    ) : Response<ImageResponse>
}