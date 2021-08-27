package com.kiriuru.weatherapp.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kiriuru.weatherapp.databinding.FragmentWeatherBinding
import com.kiriuru.weatherapp.network.RetrofitBuilder
import com.kiriuru.weatherapp.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class WeatherFragment : Fragment() {

    private val viewModel: WeatherFragmentViewModel by viewModels {
        WeatherFragmentViewModeFactory(RetrofitBuilder.apiService)
    }

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var _cityName: String = ""
    private var _cityTemp: Double = 0.0
    private var _cityDescription: String = ""
    private var _cityPressure: Int= 0
    private var _cityHumidity: Int = 0
    private var _cityWind: String = ""
    private var _cityTempMax: Double = 0.0
    private var _cityTempMin: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn.setOnClickListener { getWeather(binding.searchField.text.toString()) }

        binding.searchField.doAfterTextChanged {
            if (it != null) {
                if (it.isNotEmpty()) {
                    getWeather(it.toString())
                }
            }
        }

    }

    private fun getWeather(city: String) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCurrentWeather(city = city).collect {
                    it.let { resource ->
                        when (resource.status) {
                            Status.ERROR -> {
                            }
                            Status.LOADING -> {
                            }
                            Status.SUCCESS -> {
                                it.data?.let { data ->
                                    data.weather.forEach { weather ->
                                        _cityDescription = weather.description
                                    }
                                    _cityName = data.name
                                    _cityTemp = data.main.temp
                                    _cityHumidity = data.main.humidity
                                    _cityPressure = data.main.pressure
                                    _cityWind = data.wind.toString()
                                    _cityTempMax = data.main.tempMax
                                    _cityTempMin = data.main.tempMin
                                    setWeatherUI(
                                        city = _cityName,
                                        temp = _cityTemp,
                                        humidity = _cityHumidity,
                                        desc = _cityDescription,
                                        pressure = _cityPressure,
                                        wind = _cityWind,
                                        tMax = _cityTempMax,
                                        tMin = _cityTempMin
                                    )

                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun setWeatherUI(
        city: String, temp: Double, desc: String,
        humidity: Int, pressure: Int, wind: String, tMax: Double, tMin: Double
    ) {
        binding.cityName.text = city
        binding.cityDescription.text = desc
        binding.cityTemperature.text = tempConvert(temp)
        binding.cityHumidity.text = humidity.toString()
        binding.cityPressure.text = convertPressure(pressure)
        binding.cityWind.text = wind
        binding.cityTempMax.text = tempConvert(tMax)
        binding.cityTempMin.text = tempConvert(tMin)

    }

    private fun tempConvert(temp: Double): String {
        val tempC = (temp - 273.15) + 0.01
        return DecimalFormat("#0.00").format(tempC)
    }
    private fun convertPressure(pressure:Int):String{
        return  (pressure/1.33).toInt().toString()
    }
}

