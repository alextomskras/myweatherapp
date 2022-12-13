package com.dreamer.myweatherapp.data.repository

import android.content.res.Resources
import android.content.res.Resources.getSystem
import com.dreamer.myweatherapp.R
import com.dreamer.myweatherapp.data.mappers.toWeatherInfo
import com.dreamer.myweatherapp.data.remote.WeatherApi
import com.dreamer.myweatherapp.di.AppModule
import com.dreamer.myweatherapp.domain.repository.WeatherRepository
import com.dreamer.myweatherapp.domain.util.Resource
import com.dreamer.myweatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val resourcesProvider: AppModule.ResourcesProvider
) : WeatherRepository {

    private val errorUnk = R.string.unknown_error.toString()
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: resourcesProvider.getString(R.string.network_error))
        }
    }
}