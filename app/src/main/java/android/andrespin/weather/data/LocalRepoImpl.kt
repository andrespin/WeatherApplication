package android.andrespin.weather.data

import android.andrespin.weather.data.entities.CityForecastEntity
import android.andrespin.weather.data.room.ForecastDao
import android.andrespin.weather.domain.repo.LocalRepo

class LocalRepoImpl(private val forecastDao: ForecastDao) : LocalRepo {

    override suspend fun getAllForecasts(): List<CityForecastEntity> = forecastDao.getAllForecasts()

    override suspend fun getCityForecast(city: String): CityForecastEntity =
        forecastDao.getCityForecast(city)

    override suspend fun insertForecast(forecastEntity: CityForecastEntity) =
        forecastDao.insertForecast(forecastEntity)

    override suspend fun updateForecast(forecastEntity: CityForecastEntity) =
        forecastDao.updateForecast(forecastEntity)

    override suspend fun deleteForecast(forecastEntity: CityForecastEntity) =
        forecastDao.deleteForecast(forecastEntity)

}