package android.andrespin.weather.domain.repo

import android.andrespin.weather.data.server_responses.CityCoordinatesResponse
import android.andrespin.weather.data.server_responses.CityWeatherForecast
import retrofit2.Response

interface RemoteRepo {

    suspend fun getGeo(
        city: String,
        limit: String,
        key: String
    ): Response<CityCoordinatesResponse>

    suspend fun getCityWeather(
        q: String,
        units: String,
        appid: String
    ): Response<CityWeatherForecast>

}