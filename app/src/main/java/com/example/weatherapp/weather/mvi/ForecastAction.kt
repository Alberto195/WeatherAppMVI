package com.example.weatherapp.weather.mvi

import android.text.Editable

sealed class ForecastAction {

    data class SearchClicked(
            val query: Editable?,
            val days: Editable?
    ) : ForecastAction()

}