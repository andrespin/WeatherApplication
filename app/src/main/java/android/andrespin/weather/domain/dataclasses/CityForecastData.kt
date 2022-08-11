package android.andrespin.weather.domain.dataclasses

data class CityForecastData(
    val nameOfCity: String,
    val firstDayDate: String,
    val firstDayTemp: String,
    val secondDayDate: String,
    val secondDayTemp: String,
    val thirdDayDate: String,
    val thirdDayTemp: String,
)