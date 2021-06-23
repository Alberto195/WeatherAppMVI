package com.example.weatherapp.weather.repository

import com.example.models.ForecastModel
import com.example.models.WeatherModel

interface IWeatherRepo {
    val api: WeatherApi

    suspend fun getWeather(query: String): WeatherModel?

    suspend fun getForecast(query: String, days: String): ForecastModel?
}