package com.skyboundapps.weatherforecastapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyboundapps.weatherforecastapp.data.Weather
import com.skyboundapps.weatherforecastapp.data.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather> get() = _currentWeather

    private val _cityWeatherList = MutableLiveData<List<Weather>>()
    val cityWeatherList: LiveData<List<Weather>> get() = _cityWeatherList

    // Fetch weather by city name
    fun fetchCurrentWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val weather = repository.fetchWeather(city, apiKey)
                _currentWeather.postValue(weather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fetch weather for a list of cities
    fun fetchCitiesWeather(apiKey: String, cities: List<String>) {
        viewModelScope.launch {
            try {
                val weatherList = cities.map { city ->
                    repository.fetchWeather(city, apiKey)
                }
                _cityWeatherList.postValue(weatherList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fetch weather by coordinates
    fun fetchCurrentWeatherByCoordinates(latitude: Double, longitude: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val weather = repository.getWeatherByCoordinates(latitude, longitude, apiKey)
                _currentWeather.postValue(weather)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
