package com.kanykeinu.simpleweather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lng: Double = 0.0,
    @SerializedName("timezone")
    val timezone : String = "",
    @SerializedName("current")
    val currentWeather : CurrentWeather
)