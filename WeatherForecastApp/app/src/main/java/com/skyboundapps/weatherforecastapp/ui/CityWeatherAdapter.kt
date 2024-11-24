package com.skyboundapps.weatherforecastapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skyboundapps.weatherforecastapp.R
import com.skyboundapps.weatherforecastapp.data.Weather
import com.skyboundapps.weatherforecastapp.databinding.ItemCityWeatherBinding

class CityWeatherAdapter(private var weatherList: List<Weather>) :
    RecyclerView.Adapter<CityWeatherAdapter.WeatherViewHolder>() {

    // ViewHolder class to bind data
    class WeatherViewHolder(private val binding: ItemCityWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.tvCityName.text = weather.city
            binding.tvTemperature.text = "${weather.temperature}°C"
            binding.ivWeatherIcon.setImageResource(getWeatherIcon(weather.condition))
        }

        private fun getWeatherIcon(condition: String): Int {
            return when (condition.lowercase()) {
                "clear" -> R.drawable.sunny
                "rain" -> R.drawable.rain
                "clouds" -> R.drawable.cloud
                else -> R.drawable.sunny // Default to sunny
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemCityWeatherBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    // Function to update the weather list and notify the adapter
    fun updateWeatherList(newWeatherList: List<Weather>) {
        weatherList = newWeatherList
        notifyDataSetChanged()
    }
}
