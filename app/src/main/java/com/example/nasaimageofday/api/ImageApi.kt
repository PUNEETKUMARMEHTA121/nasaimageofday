package com.example.nasaimageofday.api

import com.example.nasaimageofday.model.ApiImageResponse
import com.example.nasaimageofday.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("/planetary/apod")
    suspend fun getImageOfDay(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<ApiImageResponse>
}