package com.dreamer.myweatherapp.di

import com.dreamer.myweatherapp.data.location.DefaultLocationTracker
import com.dreamer.myweatherapp.data.repository.WeatherRepositoryImpl
import com.dreamer.myweatherapp.domain.location.LocationTracker
import com.dreamer.myweatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}