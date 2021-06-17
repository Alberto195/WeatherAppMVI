package com.example.weatherapp.weather.viewmodels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.models.WeatherModel
import com.example.weatherapp.weather.mvi.WeatherAction
import com.example.weatherapp.weather.mvi.WeatherState
import com.example.weatherapp.weather.repository.WeatherRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WeatherViewModel: ViewModel() {
    private val repository = WeatherRepo()
    private val nextAction : MutableLiveData<WeatherAction> = MutableLiveData()
    private val viewState : MutableLiveData<WeatherState> = MutableLiveData()

    val publicViewState: LiveData<WeatherState>
        get() = viewState

    private var _weather = MutableLiveData<WeatherModel>()
    val weather: LiveData<WeatherModel>
        get() = _weather

    init {
        nextAction.observeForever {
            handleAction(it)
        }
    }

    private fun handleAction(action: WeatherAction) {
        when(action) {
            is WeatherAction.SearchClicked -> {
                viewState.postValue(WeatherState.Loading)
                handleSearch(action)
            }
        }
    }

    private suspend fun getWeather(q: String) {
        _weather.value = repository.getWeather(q)
    }

    private fun handleSearch(action: WeatherAction.SearchClicked) {
        when {
            action.query.isNullOrEmpty() -> {
                viewState.postValue(WeatherState.ErrorOccured)
            }

            else -> {
                CoroutineScope(Dispatchers.Main).launch {
                    getWeather(action.query.toString())
                    viewState.postValue(WeatherState.Loaded)
                }
            }
        }
    }

    fun dispatch(action: WeatherAction) {
        nextAction.value = action
    }
}