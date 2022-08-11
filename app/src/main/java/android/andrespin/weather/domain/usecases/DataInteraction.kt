package android.andrespin.weather.domain.usecases

import android.andrespin.weather.data.entities.CityForecastEntity
import android.andrespin.weather.data.server_responses.CityWeatherForecast
import android.andrespin.weather.domain.dataclasses.CityCoordinatesData
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.domain.utils.Result
import retrofit2.Response

interface DataInteraction {

    suspend fun getCityCoordinates(
        city: String,
        limit: String,
        key: String
    ) : Result<CityCoordinatesData>

    suspend fun getCityWeather(
        q: String,
        units: String,
        appid: String
    ): Result<CityForecastData>

    suspend fun updateListOfCityWeather(list: List<CityForecastData>)

    suspend fun addForecastToDb(forecast: CityForecastData)

    suspend fun getAllForecastsLocal(): List<CityForecastEntity>

    suspend fun getCityForecastLocal(city: String): CityForecastEntity
}