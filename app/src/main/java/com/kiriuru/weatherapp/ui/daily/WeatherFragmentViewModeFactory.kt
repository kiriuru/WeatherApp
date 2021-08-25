package com.kiriuru.weatherapp.ui.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kiriuru.weatherapp.network.WeatherApi
import com.kiriuru.weatherapp.repository.WeatherRepository
import java.lang.IllegalArgumentException

class WeatherFragmentViewModeFactory(private val apiHelper: WeatherApi) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherFragmentViewModel::class.java)) {
            WeatherFragmentViewModel(weatherRepository = WeatherRepository(apiHelper)) as T
        } else {
            throw IllegalArgumentException("viewModel not found")
        }
    }


}