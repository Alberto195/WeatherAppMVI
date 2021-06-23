package com.example.weatherapp.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.models.ForecastModel
import com.example.weatherapp.weather.mvi.ForecastAction
import com.example.weatherapp.weather.mvi.WeatherState
import com.example.weatherapp.weather.repository.IWeatherRepo
import com.example.weatherapp.weather.repository.WeatherRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForecastViewModel: ViewModel() {

    private var repository : IWeatherRepo
    private val nextAction : MutableLiveData<ForecastAction> = MutableLiveData()
    private val viewState : MutableLiveData<WeatherState> = MutableLiveData()

    val publicViewState: LiveData<WeatherState>
        get() = viewState

    private var _forecast = MutableLiveData<ForecastModel>()
    val forecast: LiveData<ForecastModel>
        get() = _forecast

    init {
        nextAction.observeForever {
            handleAction(it)
        }
        repository = WeatherRepo()
    }

    private fun handleAction(action: ForecastAction) {
        when(action) {
            is ForecastAction.SearchClicked -> {
                viewState.postValue(WeatherState.Loading)
                handleSearch(action)
            }
        }
    }

    private suspend fun getForecast(q: String, days: String) {
        _forecast.value = repository.getForecast(q, days)
    }

    private fun handleSearch(action: ForecastAction.SearchClicked) {
        when {
            action.query.isNullOrEmpty() -> {
                viewState.postValue(WeatherState.ErrorOccured)
            }

            else -> {
                CoroutineScope(Dispatchers.Main).launch {
                    getForecast(action.query.toString(), action.days.toString())
                    viewState.postValue(WeatherState.Loaded)
                }
            }
        }
    }

    fun dispatch(action: ForecastAction) {
        nextAction.value = action
    }

}