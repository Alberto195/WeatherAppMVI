package com.example.weatherapp.weather.repository

import com.example.models.ForecastModel
import com.example.models.WeatherModel
import java.lang.Exception

class WeatherRepo(override val api: WeatherApi = WeatherApi()) : IWeatherRepo {

    override suspend fun getWeather(query: String): WeatherModel? {
        val weather = api.getWeather(API_KEY, query)
        return if (weather.isSuccessful) {
            weather.body()!!
        } else {
             null
        }
    }

    override suspend fun getForecast(query: String, days: String): ForecastModel? {
        val forecast = api.getWeatherForWeek(API_KEY, query, days)
        return if (forecast.isSuccessful) {
            forecast.body()!!
        } else {
            null
        }
    }

    companion object {
        const val API_KEY = "307a33768797473183e145420211606"
    }
}