package android.andrespin.weather.data

import android.andrespin.weather.data.api.GeoApi
import android.andrespin.weather.data.api.WeatherApi
import android.andrespin.weather.data.server_responses.CityWeatherForecast
import android.andrespin.weather.domain.repo.RemoteRepo
import retrofit2.Response

class RemoteRepoImpl(private val geoApi: GeoApi, private val weatherApi: WeatherApi) : RemoteRepo {

    override suspend fun getGeo(
        city: String,
        limit: String,
        key: String
    ) = geoApi.getCityCoordinates(city, limit, key)


    override suspend fun getCityWeather(
        q: String,
        units: String,
        appid: String
    ): Response<CityWeatherForecast> = weatherApi.getCityWeatherForecast(q, units, appid)

}