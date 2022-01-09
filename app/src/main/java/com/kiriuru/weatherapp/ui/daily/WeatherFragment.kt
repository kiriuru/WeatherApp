package com.kiriuru.weatherapp.ui.daily

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kiriuru.weatherapp.databinding.FragmentWeatherBinding
import com.kiriuru.weatherapp.network.RetrofitBuilder
import com.kiriuru.weatherapp.utils.Status
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {

    private val viewModel: WeatherFragmentViewModel by viewModels {
        WeatherFragmentViewModeFactory(RetrofitBuilder.apiService)
    }

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = checkNotNull(_binding)

    @SuppressLint("SimpleDateFormat")
    private var currentData = SimpleDateFormat("dd/MM/yyyy").format(Date())


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
        getWeather()
        binding.btn.setOnClickListener { getWeather() }
    }

    private fun getWeather() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCurrentWeather("Екатеринбург").collect {
                    it.let { resource ->
                        when (resource.status) {
                            Status.ERROR -> {
                            }
                            Status.LOADING -> {
                            }
                            Status.SUCCESS -> {
                                binding.currentWeatherText.text =
                                    it.data?.weather?.get(0)?.description
                                binding.currentData.text = currentData
                                binding.currentFeelsTemp.text =
                                    it.data?.main?.feelsLike?.let { feelsLike ->
                                        setTemp(feelsLike)
                                    }
                                binding.currentTemp.text = it.data?.main?.temp?.let { curTemp ->
                                    setTemp(
                                        curTemp
                                    )
                                }
                                setImage(it.data?.weather?.get(0)?.icon.toString())
                            }
                        }
                    }

                }
            }
        }
    }

    private fun setTemp(temp: Double): String {
        val tempC = (temp - 273.15) + 0.01
        return DecimalFormat("#0.0 ºC").format(tempC)
    }

    private fun setImage(icon: String) {
        Picasso.get().load("http://openweathermap.org/img/wn/$icon@2x.png")
            .into(binding.currentImageView)

    }
}

