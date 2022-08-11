package android.andrespin.weather.domain.repo

import android.andrespin.weather.data.entities.CityForecastEntity

interface LocalRepo  {

    suspend fun getAllForecasts(): List<CityForecastEntity>

    suspend fun getCityForecast(city: String): CityForecastEntity

    suspend fun insertForecast(forecastEntity: CityForecastEntity)

    suspend fun updateForecast(forecastEntity: CityForecastEntity)

    suspend fun deleteForecast(forecastEntity: CityForecastEntity)

}