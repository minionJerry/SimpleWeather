package com.kanykeinu.simpleweather.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("dt")
    val date: Long = 0,
    @SerializedName("temp")
    val temp : Double = 0.0,
    @SerializedName("uvi")
    val uvIndex : Double = 0.0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0,
    @SerializedName("weather")
    val weather: List<Weather>
)
