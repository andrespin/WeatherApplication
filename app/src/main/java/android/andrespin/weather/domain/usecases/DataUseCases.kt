package android.andrespin.weather.domain.usecases

import android.andrespin.weather.data.entities.CityForecastEntity
import android.andrespin.weather.data.server_responses.CityWeatherForecast
import android.andrespin.weather.domain.dataclasses.CityCoordinatesData
import android.andrespin.weather.domain.dataclasses.CityForecastData
import android.andrespin.weather.domain.repo.LocalRepo
import android.andrespin.weather.domain.repo.RemoteRepo
import android.andrespin.weather.domain.utils.Result
import android.andrespin.weather.domain.utils.convertToCityCoordinatesData
import android.andrespin.weather.domain.utils.convertToCityForecastData
import kotlinx.coroutines.CoroutineStart
import java.lang.Exception

class DataUseCases(private val remote: RemoteRepo, private val local: LocalRepo) : DataInteraction {

    override suspend fun getCityCoordinates(
        city: String,
        limit: String,
        key: String,
    ): Result<CityCoordinatesData> {
        val response = remote.getGeo(city, limit, key)
        return if (response.isSuccessful) {
            try {
                val res = Result.Success(convertToCityCoordinatesData(response.body()!!, city))
                res
            } catch (e: Exception) {
                Result.Failure(Exception(response.message()))
            }
        } else {
            Result.Failure(Exception(response.message()))
        }
    }

    override suspend fun getCityWeather(
        q: String,
        units: String,
        appid: String,
    ): Result<CityForecastData> {
        val response = remote.getCityWeather(q, units, appid)
        println("response.body() " + response.body())
        if (response.isSuccessful) {
            val conv = convertToCityForecastData(response.body()!!)
            return Result.Success(conv)
        } else {
            return Result.Failure(Exception(response.message()))
        }
    }

    override suspend fun updateListOfCityWeather(list: List<CityForecastData>) =
        list.indices.forEach { i ->
            addForecastToDb(list[i])
        }

    override suspend fun getAllForecastsLocal() = local.getAllForecasts()

    override suspend fun getCityForecastLocal(city: String) = local.getCityForecast(city)

    override suspend fun addForecastToDb(forecast: CityForecastData) {
        try {
            insert(forecast)
        } catch (e: Exception) {
            update(forecast)
        }
    }

    private suspend fun insert(forecast: CityForecastData) {
        local.insertForecast(
            CityForecastEntity(
                forecast.nameOfCity,
                forecast.firstDayDate,
                forecast.firstDayTemp,
                forecast.secondDayDate,
                forecast.secondDayTemp,
                forecast.thirdDayDate,
                forecast.thirdDayTemp
            )
        )
    }

    private suspend fun update(forecast: CityForecastData) {
        local.updateForecast(
            CityForecastEntity(
                forecast.nameOfCity,
                forecast.firstDayDate,
                forecast.firstDayTemp,
                forecast.secondDayDate,
                forecast.secondDayTemp,
                forecast.thirdDayDate,
                forecast.thirdDayTemp
            )
        )
    }

}
