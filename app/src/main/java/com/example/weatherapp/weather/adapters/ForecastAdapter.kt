package com.example.weatherapp.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.models.ForecastModel
import com.example.weatherapp.R

class ForecastAdapter()
    : RecyclerView.Adapter<ForecastAdapter.ForecastHolder>()
{
    private var forecastList = emptyList<ForecastModel.Forecast.Forecastday>()

    fun setList(list: List<ForecastModel.Forecast.Forecastday>) {
        forecastList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_item, parent, false)
        return ForecastHolder(view)
    }
    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        holder.bind(position, forecastList)
    }

    override fun getItemCount(): Int = forecastList.size

    class ForecastHolder(
            view: View,
    ) : RecyclerView.ViewHolder(view) {

        private val date: TextView = view.findViewById(R.id.date)
        private val humidity: TextView = view.findViewById(R.id.humidity_adapt)
        private val temp: TextView = view.findViewById(R.id.temp_adapt)

        fun bind(position: Int, forecast: List<ForecastModel.Forecast.Forecastday>) {
            date.text = forecast[position].date
            temp.text = forecast[position].day.avgtempC.toString()
            humidity.text = forecast[position].day.avghumidity.toString()
        }
    }
}