package com.kiriuru.weatherapp.ui.daily

import androidx.lifecycle.ViewModel
import com.kiriuru.weatherapp.repository.WeatherRepository
import com.kiriuru.weatherapp.utils.Resource
import kotlinx.coroutines.flow.flow

class WeatherFragmentViewModel(private val weatherRepository: WeatherRepository):ViewModel() {

    fun getCurrentWeather(city: String) = flow {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = weatherRepository.getCurrentWeather(city)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }
}