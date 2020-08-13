package com.kanykeinu.simpleweather.data.network

import com.kanykeinu.simpleweather.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "cd8eb75dc9af795cbeb5e149d4bc0438"
private const val PARTS = "minutely,hourly,daily"
private const val UNITS = "metric"

interface WeatherApi {

    @GET("data/2.5/onecall")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("appid") key: String = API_KEY,
        @Query("exclude") parts: String = PARTS,
        @Query("units") units: String = UNITS
    ): Single<WeatherResponse>
}