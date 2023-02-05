package com.example.snowfinder.api

import com.example.snowfinder.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v2.0/current")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("key") key: String): Response<Weather>
}