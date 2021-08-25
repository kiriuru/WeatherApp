package com.kiriuru.weatherapp.network

import com.kiriuru.weatherapp.entity.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    suspend fun getWeatherCity(
        @Query("appId") appId: String = "1e0674db98d2e54cab279772a669f8d2",
        @Query("lang") language: String = "ru",
        @Query("q") cityName: String
    ): WeatherData
}