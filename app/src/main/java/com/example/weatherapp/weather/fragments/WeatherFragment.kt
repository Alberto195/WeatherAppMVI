package com.example.weatherapp.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.weatherapp.databinding.WeatherFragmentBinding
import com.example.weatherapp.weather.mvi.WeatherAction
import com.example.weatherapp.weather.mvi.WeatherState
import com.example.weatherapp.weather.viewmodels.WeatherViewModel

open class WeatherFragment: Fragment(), IView<WeatherState> {

    private lateinit var binding: WeatherFragmentBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = WeatherViewModel()
        viewModel.publicViewState.observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.weather.observe(viewLifecycleOwner, {
            it?.let {
                binding.city.text = it.location.name
                binding.humidity.text = it.current.humidity.toString()
                binding.temp.text = it.current.tempC.toString()
            }
        })

        binding.searchButton.setOnClickListener {
            dispatchAction(WeatherAction.SearchClicked(
                binding.query.text
            ))
        }
    }

    override fun render(state: WeatherState) {
        when(state) {
            //TODO do stuff to ui
            is WeatherState.Loading -> { binding.searchLoader.visibility = VISIBLE }
            is WeatherState.Loaded -> { binding.searchLoader.visibility = INVISIBLE }
            is WeatherState.ErrorOccured -> {
                Toast.makeText(requireContext(), "errorMessage", Toast.LENGTH_SHORT).show()
                binding.searchLoader.visibility = INVISIBLE
            }
        }
    }

    private fun dispatchAction(action: WeatherAction) = viewModel.dispatch(action)

}