package com.example.weatherapp.weather.repository

import com.example.models.WeatherModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getMovies(
        @Query("key") key: String,
        @Query("q") q: String
    ): Response<WeatherModel>

    companion object {
        operator fun invoke(): WeatherApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(WeatherApi::class.java)
        }
        private const val BASE_URL = "https://api.weatherapi.com/v1/"
    }
}