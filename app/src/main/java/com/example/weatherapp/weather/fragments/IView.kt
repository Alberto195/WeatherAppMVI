package com.example.weatherapp.weather.fragments

import com.example.weatherapp.weather.mvi.IState

interface IView<S: IState> {
    fun render(state: S)
}