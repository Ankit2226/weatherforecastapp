package com.skyboundapps.weatherforecastapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skyboundapps.weatherforecastapp.databinding.ActivityCityDetailsBinding

class CityDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data passed from MainActivity
        val cityName = intent.getStringExtra("cityName")
        val temperature = intent.getStringExtra("temperature")
        val condition = intent.getStringExtra("condition")
        val country = intent.getStringExtra("country")

        // Set data to UI
        binding.tvCityName.text = cityName
        binding.tvCountry.text = country
        binding.tvTemperature.text = "Temperature: $temperature°C"
        binding.tvCondition.text = "Condition: $condition"
    }
}
