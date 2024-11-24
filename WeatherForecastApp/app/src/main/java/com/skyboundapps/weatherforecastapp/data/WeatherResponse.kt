data class WeatherResponse(
    val name: String,  // City name
    val main: Main,
    val weather: List<WeatherCondition>,
    val sys: Sys  // Add this field for sys
)

data class Sys(
    val country: String  // Country code (e.g., "US")
)

data class Main(
    val temp: Double  // Temperature
)

data class WeatherCondition(
    val description: String  // Weather description
)