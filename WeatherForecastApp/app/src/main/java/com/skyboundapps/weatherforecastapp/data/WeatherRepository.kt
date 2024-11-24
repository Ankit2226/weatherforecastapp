package com.skyboundapps.weatherforecastapp.data

class WeatherRepository(private val weatherApi: WeatherApiService) {

    suspend fun fetchWeather(city: String, apiKey: String): Weather {
        val response = weatherApi.getWeather(city, apiKey)
        return Weather(
            city = response.name,
            country = response.sys.country,  // Correctly pass the country
            temperature = response.main.temp.toInt(),
            condition = response.weather[0].description
        )
    }

    suspend fun getWeatherByCoordinates(latitude: Double, longitude: Double, apiKey: String): Weather {
        val response = weatherApi.getWeatherByCoordinates(latitude, longitude, apiKey)
        return Weather(
            city = response.name,
            country = response.sys.country,  // Correctly pass the country
            temperature = response.main.temp.toInt(),
            condition = response.weather[0].description
        )
    }
}