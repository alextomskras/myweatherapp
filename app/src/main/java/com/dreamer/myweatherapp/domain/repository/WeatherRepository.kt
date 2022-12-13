package com.dreamer.myweatherapp.domain.repository

import com.dreamer.myweatherapp.domain.util.Resource
import com.dreamer.myweatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}