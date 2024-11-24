package com.skyboundapps.weatherforecastapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.skyboundapps.weatherforecastapp.data.WeatherApi
import com.skyboundapps.weatherforecastapp.data.WeatherRepository
import com.skyboundapps.weatherforecastapp.databinding.ActivityMainBinding
import com.skyboundapps.weatherforecastapp.ui.CityWeatherAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.skyboundapps.weatherforecastapp.viewmodel.WeatherViewModel
import com.skyboundapps.weatherforecastapp.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherApi.retrofitService))
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val API_KEY = "e73731d66bd3c94e2bae24f6d20a1f2f" // Replace with your API key
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Check for location permissions
        checkLocationPermission()

        // Set up RecyclerView with a vertical LinearLayoutManager
        binding.rvCities.layoutManager = LinearLayoutManager(this)

        // Observe current weather data and update the UI
        weatherViewModel.currentWeather.observe(this) { weather ->
            weather?.let {
                binding.tvLocation.text = "${it.city}, ${it.country}"
                binding.tvTemperature.text = "Temp: ${it.temperature}°C"
                binding.tvCondition.text = "Condition: ${it.condition}"
            }
        }

        // Observe the list of cities' weather and update the RecyclerView adapter
        weatherViewModel.cityWeatherList.observe(this) { weatherList ->
            val adapter = CityWeatherAdapter(weatherList)
            binding.rvCities.adapter = adapter

            // Call the updateWeatherList method if data is updated
            adapter.updateWeatherList(weatherList) // This will refresh the RecyclerView with new data
        }

        // Fetch weather data for a predefined list of cities
        val cities = listOf("New York", "London", "Paris", "Mumbai", "Tokyo")
        weatherViewModel.fetchCitiesWeather(API_KEY, cities)

        // Fetch weather data for the searched city
        binding.etSearch.setOnEditorActionListener { v, _, _ ->
            val city = v.text.toString()
            if (city.isNotEmpty()) {
                weatherViewModel.fetchCurrentWeather(city, API_KEY)
            }
            true
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    weatherViewModel.fetchCurrentWeatherByCoordinates(latitude, longitude, API_KEY)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }
}
