package com.skyboundapps.weatherforecastapp.data

data class Weather(
    val city: String,
    val country: String,  // Add this field for the country
    val temperature: Int,
    val condition: String
)