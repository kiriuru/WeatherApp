package com.kiriuru.weatherapp.repository

import com.kiriuru.weatherapp.network.WeatherApi

class WeatherRepository(private val api: WeatherApi) {
suspend fun getCurrentWeather(city:String) = api.getWeatherCity(cityName = city)
}