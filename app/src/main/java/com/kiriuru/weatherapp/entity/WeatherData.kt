package com.kiriuru.weatherapp.entity

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class WeatherData(
    @SerializedName("code") val code: Int,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("sys") val systemData: SystemData,
    @SerializedName("dt") val dt: Int,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("main") val main: Main,
    @SerializedName("base") val base: String,
    @SerializedName("weather") val weather: List<WeatherItem>,
    @SerializedName("coord") val coord: Coord
) {
}