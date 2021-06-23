package com.example.weatherapp.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.models.ForecastModel
import com.example.weatherapp.databinding.ForecastFragmentBinding
import com.example.weatherapp.weather.adapters.ForecastAdapter
import com.example.weatherapp.weather.mvi.ForecastAction
import com.example.weatherapp.weather.mvi.WeatherAction
import com.example.weatherapp.weather.mvi.WeatherState
import com.example.weatherapp.weather.viewmodels.ForecastViewModel

class ForecastFragment: Fragment(), IView<WeatherState> {

    private lateinit var binding: ForecastFragmentBinding
    private lateinit var viewModel: ForecastViewModel
    private lateinit var adapterForecast: ForecastAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = ForecastFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ForecastViewModel()
        adapterForecast = ForecastAdapter()

        viewModel.publicViewState.observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.forecast.observe(viewLifecycleOwner, {
            it?.let {
                binding.forecasts.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapterForecast.setList(it.forecast.forecastday)
                    adapter = adapterForecast

                }
            }
        })

        binding.searchButton.setOnClickListener {
            dispatchAction(ForecastAction.SearchClicked(
                    binding.query.text,
                    binding.numOfDays.text
            ))
        }
    }

    override fun render(state: WeatherState) {
        when(state) {
            is WeatherState.Loading -> { binding.searchLoader.visibility = View.VISIBLE
            }
            is WeatherState.Loaded -> { binding.searchLoader.visibility = View.INVISIBLE
            }
            is WeatherState.ErrorOccured -> {
                Toast.makeText(requireContext(), "errorMessage", Toast.LENGTH_SHORT).show()
                binding.searchLoader.visibility = View.INVISIBLE
            }
        }
    }

    private fun dispatchAction(action: ForecastAction) = viewModel.dispatch(action)

}