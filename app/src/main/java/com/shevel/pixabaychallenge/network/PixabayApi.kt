package com.shevel.pixabaychallenge.network

import com.shevel.pixabaychallenge.model.SearchImagesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET(".")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 100
    ): SearchImagesResponse
}