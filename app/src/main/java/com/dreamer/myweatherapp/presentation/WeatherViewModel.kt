package com.dreamer.myweatherapp.presentation

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.getSystem
import android.content.res.loader.ResourcesProvider
import android.provider.Settings.Global.getString
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreamer.myweatherapp.R
import com.dreamer.myweatherapp.di.AppModule
import com.dreamer.myweatherapp.domain.location.LocationTracker
import com.dreamer.myweatherapp.domain.repository.WeatherRepository
import com.dreamer.myweatherapp.domain.util.Resource
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope.coroutineContext

import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val resourcesProvider: AppModule.ResourcesProvider
): ViewModel() {
    var state by mutableStateOf(WeatherState())
        private set
//    val netError = R.string.network_error.toString()

//    val context = ApplicationContext() as Context
//    val netError = getApplication(context).resources.getString(R.string.network_error).toString()

    fun loadWeatherInfo() {
//        val contentString = ObservableInt()
//
//        contentString.set(R.string.network_error)
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,

                error = resourcesProvider.getString(R.string.network_error)
                )
            }
        }
    }
}