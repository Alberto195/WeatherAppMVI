package com.example.weatherapp.weather.mvi

sealed class WeatherState: IState {
    object Loading : WeatherState()
    object Loaded : WeatherState()
    object ErrorOccured : WeatherState()
}