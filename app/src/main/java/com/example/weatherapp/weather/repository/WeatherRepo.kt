package com.example.weatherapp.weather.repository

import com.example.models.WeatherModel
import java.lang.Exception

class WeatherRepo(override val api: WeatherApi = WeatherApi()) : IWeatherRepo {

    override suspend fun getWeather(query: String): WeatherModel? {
        val weather = api.getMovies(API_KEY, query)
        return if (weather.isSuccessful) {
            weather.body()!!
        } else {
             null
        }
    }

    companion object {
        const val API_KEY = "307a33768797473183e145420211606"
    }
}