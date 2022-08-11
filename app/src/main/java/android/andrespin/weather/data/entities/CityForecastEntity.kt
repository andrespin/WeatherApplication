package android.andrespin.weather.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts_table")
data class CityForecastEntity(
    @PrimaryKey
    var nameOfCity: String,
    var firstDayDate: String,
    var firstDayTemp: String,
    var secondDayDate: String,
    var secondDayTemp: String,
    var thirdDayDate: String,
    var thirdDayTemp: String
)
