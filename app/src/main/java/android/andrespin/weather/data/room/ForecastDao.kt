package android.andrespin.weather.data.room

import android.andrespin.weather.data.entities.CityForecastEntity
import androidx.room.*

@Dao
interface ForecastDao {

    @Insert
    suspend fun insertForecast(cityForecastEntity: CityForecastEntity)

    @Delete
    suspend fun deleteForecast(cityForecastEntity: CityForecastEntity)

    @Update
    suspend fun updateForecast(cityForecastEntity: CityForecastEntity)

    @Query("SELECT * FROM forecasts_table WHERE nameOfCity=:nameOfCity")
    suspend fun getCityForecast(nameOfCity: String): CityForecastEntity

    @Query("SELECT * FROM forecasts_table")
    suspend fun getAllForecasts(): List<CityForecastEntity>

}

