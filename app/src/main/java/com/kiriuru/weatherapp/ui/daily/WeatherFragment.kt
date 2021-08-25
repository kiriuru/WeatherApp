package com.kiriuru.weatherapp.ui.daily

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class WeatherFragment : Fragment() {

    private val viewModel: WeatherFragmentViewModel by viewModels {
        WeatherFragmentViewModeFactory(RetrofitBuilder.apiService)
    }

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = checkNotNull(_binding)


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
        binding.btn.setOnClickListener { getWeather() }
    }

    fun getWeather() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCurrentWeather("Kioto").collect {
                    it.let { resource ->
                        when (resource.status) {
                            Status.ERROR -> {
                            }
                            Status.LOADING -> {
                            }
                            Status.SUCCESS -> {
                                binding.textView.text = it.data?.main?.temp.toString()
                            }
                        }
                    }

                }
            }
        }
    }
}

