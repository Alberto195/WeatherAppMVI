package com.example.weatherapp.weather.mvi

import android.text.Editable

sealed class WeatherAction {
    data class SearchClicked(
        val query: Editable?,
    ) : WeatherAction()
}