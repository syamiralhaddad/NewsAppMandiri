package com.rakamin.mandirinews.network

import com.rakamin.mandirinews.data.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    companion object {
        const val API_KEY = "167595d5a29148e78a8f0f28cfc2862e"
        const val Q = "Bank"
    }

    @GET("top-headlines")
    fun getHeadline(
        @Query("q") q: String = Q,
        @Query("pageSize") pageSize: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET("everything")
    fun getEverything(
        @Query("pageSize") pageSize: Int,
        @Query("q") q: String = Q,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Call<NewsResponse>
}