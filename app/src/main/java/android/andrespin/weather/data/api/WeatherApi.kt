package android.andrespin.weather.data.api

import android.andrespin.weather.data.server_responses.CityWeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast?")
    suspend fun getCityWeatherForecast(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ) : Response<CityWeatherForecast>

}